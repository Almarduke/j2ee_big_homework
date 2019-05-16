package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.enums.AddressStatus;
import nju.sephidator.yummybackend.enums.OrderStatus;
import nju.sephidator.yummybackend.exceptions.FoodInsufficientException;
import nju.sephidator.yummybackend.exceptions.MemberAmountException;
import nju.sephidator.yummybackend.model.*;
import nju.sephidator.yummybackend.repository.*;
import nju.sephidator.yummybackend.service.MemberService;
import nju.sephidator.yummybackend.service.OrderService;
import nju.sephidator.yummybackend.utils.KeyUtil;
import nju.sephidator.yummybackend.utils.MathUtil;
import nju.sephidator.yummybackend.utils.TimeUtil;
import nju.sephidator.yummybackend.vo.order.OrderDetailVO;
import nju.sephidator.yummybackend.vo.order.OrderInfoVO;
import nju.sephidator.yummybackend.vo.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderJPA orderJPA;

    @Autowired
    private OrderDetailJPA orderDetailJPA;

    @Autowired
    private RestaurantJPA restaurantJPA;

    @Autowired
    private AddressJPA addressJPA;

    @Autowired
    private AddressLinkJPA addressLinkJPA;

    @Autowired
    private MemberJPA memberJPA;

    @Autowired
    private FoodJPA foodJPA;

    @Autowired
    private YummyFinanceJPA yummyFinanceJPA;

    @Autowired
    private MemberService memberService;

    @Override
    public synchronized String submit(String restaurantId, String memberEmail, Double totalAmount, Double discount, List<OrderDetailVO> orderDetailVOList) {
        Order order = new Order();
        order.setId(KeyUtil.generateUniqueKey());
        order.setMemberEmail(memberEmail);
        order.setMemberAddress(addressLinkJPA.findByUserIdAndStatus
                        (memberEmail, AddressStatus.DEFAULT.getCode())
                        .get(0).getAddressName());
        order.setRestaurantId(restaurantId);
        order.setAmount(totalAmount);
        order.setDiscount(discount);
        order.setOrderStatus(OrderStatus.TOPAY.getCode());
        order.setCreateTime(new Date());
        orderJPA.save(order);

        for (OrderDetailVO orderDetailVO: orderDetailVOList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            orderDetail.setFoodId(orderDetailVO.getFoodId());
            orderDetail.setFoodNum(orderDetailVO.getFoodNum());
            orderDetail.setPrice(orderDetailVO.getPrice());
            orderDetailJPA.save(orderDetail);
        }

        return generateAlertMessage(
                restaurantJPA.getOne(restaurantId).getAddress(),
                order.getMemberAddress());
    }

    @Override
    public List<OrderVO> findMemberOrders(String email, Integer orderStatus) {
        List<OrderVO> result = generateVOFromOrderDAO(orderJPA
                .findByMemberEmailAndOrderStatus(email, orderStatus));
        Collections.reverse(result);
        return result;
    }

    @Override
    public List<OrderVO> findRestaurantOrders(String restaurantId, Integer orderStatus) {
        List<OrderVO> result = generateVOFromOrderDAO(orderJPA
                .findByRestaurantIdAndOrderStatus(restaurantId, orderStatus));
        Collections.reverse(result);
        return result;
    }

    @Override
    public OrderInfoVO getOrderInfo(String orderId) {
        OrderInfoVO orderInfo = new OrderInfoVO();
        Order order = orderJPA.getOne(orderId);
        orderInfo.setId(orderId);
        orderInfo.setCreateTime(TimeUtil.timeFormat(order.getCreateTime()));
        orderInfo.setOrderStatus(OrderStatus.getDescription(order.getOrderStatus()));
        orderInfo.setRestaurantName(restaurantJPA.getOne(order.getRestaurantId()).getName());
        orderInfo.setMemberAddress(order.getMemberAddress());
        orderInfo.setMemberName(memberJPA.getOne(order.getMemberEmail()).getName());
        orderInfo.setMemberPhone(memberJPA.getOne(order.getMemberEmail()).getPhone());
        orderInfo.setTotalAmount(order.getAmount());
        orderInfo.setDiscount(order.getDiscount());

        List<OrderDetailVO> orderDetails = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailJPA.findByOrderId(orderId)) {
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            orderDetail.setOrderId(orderId);
            orderDetailVO.setFoodName(foodJPA.findDistinctById(orderDetail.getFoodId()).getName());
            orderDetailVO.setFoodNum(orderDetail.getFoodNum());
            orderDetailVO.setPrice(orderDetail.getPrice());
            orderDetails.add(orderDetailVO);
        }
        orderInfo.setOrderDetails(orderDetails);

        return orderInfo;
    }

    @Override
    public List<OrderVO> payOrder(String id) {
        Order order = orderJPA.getOne(id);
        Member member = memberJPA.getOne(order.getMemberEmail());
        if (member.getAmount() < order.getAmount()) {
            throw new MemberAmountException();
        }

        for (OrderDetail orderDetail: orderDetailJPA.findByOrderId(id)) {
            Food food = foodJPA.findDistinctById(orderDetail.getFoodId());
            if (food.getNumber() < orderDetail.getFoodNum()) {
                throw new FoodInsufficientException();
            }
        }
        for (OrderDetail orderDetail: orderDetailJPA.findByOrderId(id)) {
            Food food = foodJPA.findDistinctById(orderDetail.getFoodId());
            food.setNumber(food.getNumber() - orderDetail.getFoodNum());
            foodJPA.save(food);
        }

        Double moneyToRestaurant = order.getAmount() * 0.4;
        Double moneyToYummy = order.getAmount() - moneyToRestaurant - order.getDiscount();

        member.setAmount(member.getAmount() - (moneyToRestaurant + moneyToYummy));
        memberJPA.save(member);

        Restaurant restaurant = restaurantJPA.getOne(order.getRestaurantId());
        restaurant.setAmount(restaurant.getAmount() + moneyToRestaurant);
        restaurantJPA.save(restaurant);

        YummyFinance financeDAO = new YummyFinance();
        financeDAO.setIncome(moneyToYummy);
        financeDAO.setOrderId(order.getId());
        financeDAO.setTime(new Date());
        yummyFinanceJPA.save(financeDAO);

        return updateOrder(id, OrderStatus.PAYED.getCode(), true);
    }

    @Override
    public List<OrderVO> handleOrder(String id) {
        return updateOrder(id, OrderStatus.DISTRIBUTING.getCode(), false);
    }

    @Override
    public List<OrderVO> finishOrder(String id) {
        Order order = orderJPA.getOne(id);
        Double moneyToRestaurant = order.getAmount() * 0.5;

        Restaurant restaurant = restaurantJPA.getOne(order.getRestaurantId());
        restaurant.setAmount(restaurant.getAmount() + moneyToRestaurant);
        restaurantJPA.save(restaurant);

        YummyFinance financeDAO = new YummyFinance();
        financeDAO.setIncome(-moneyToRestaurant);
        financeDAO.setOrderId(order.getId());
        financeDAO.setTime(new Date());
        yummyFinanceJPA.save(financeDAO);

        return updateOrder(id, OrderStatus.FINISHED.getCode(), true);
    }

    @Override
    public List<OrderVO> cancelOrder(String id) {
        Order order = orderJPA.getOne(id);
        if (!order.getOrderStatus().equals(OrderStatus.TOPAY.getCode()) ||
                !order.getOrderStatus().equals(OrderStatus.CANCELLED.getCode())) {
            Double moneyBack = order.getAmount() * 0.5;

            Member member = memberJPA.getOne(order.getMemberEmail());
            member.setAmount(member.getAmount() + moneyBack);
            memberJPA.save(member);

            YummyFinance financeDAO = new YummyFinance();
            financeDAO.setIncome(-moneyBack);
            financeDAO.setOrderId(order.getId());
            financeDAO.setTime(new Date());
            yummyFinanceJPA.save(financeDAO);

            for (OrderDetail orderDetail: orderDetailJPA.findByOrderId(id)) {
                Food food = foodJPA.findDistinctById(orderDetail.getFoodId());
                food.setNumber(food.getNumber() + orderDetail.getFoodNum());
                foodJPA.save(food);
            }
        }
        return updateOrder(id, OrderStatus.CANCELLED.getCode(), true);
    }

    private List<OrderVO> updateOrder(String id, Integer newStatus, boolean isMemberEditing) {
        Order order = orderJPA.getOne(id);
        Integer oldStatus = order.getOrderStatus();
        order.setOrderStatus(newStatus);
        orderJPA.save(order);
        memberService.updateMemberLevel(order.getMemberEmail());
        return isMemberEditing ? findMemberOrders(id, oldStatus) : findRestaurantOrders(id, oldStatus);
    }

    private List<OrderVO> generateVOFromOrderDAO(List<Order> orderList) {
        List<OrderVO> result = new ArrayList<>();
        for (Order order : orderList) {
            OrderVO orderVO = new OrderVO();
            orderVO.setId(order.getId());
            orderVO.setRestaurantName(restaurantJPA.getOne(order.getRestaurantId()).getName());
            orderVO.setTime(TimeUtil.timeFormat(order.getCreateTime()));
            orderVO.setActualAmount(order.getAmount() - order.getDiscount());
            orderVO.setStatus(OrderStatus.getDescription(order.getOrderStatus()));
            result.add(orderVO);
        }
        return result;
    }

    private String generateAlertMessage(String restaurantAddressName, String memberAddressName) {
        Address restaurantAddress = addressJPA.getOne(restaurantAddressName);
        Address memberAddress = addressJPA.getOne(memberAddressName);
        Double distance = MathUtil.getDistance(restaurantAddress.getCoordinateX(),
                restaurantAddress.getCoordinateY(),
                memberAddress.getCoordinateX(),
                memberAddress.getCoordinateY());
        int minutes = 15 + 5 * distance.intValue();
        return "提交订单成功，" +
                "饭店距离您大约" + distance + "公里，" +
                "送餐时间大约" + minutes + "分钟，" +
                "请在15分钟内完成支付";
    }
}
