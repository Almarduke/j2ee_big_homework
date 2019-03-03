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
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.setId(KeyUtil.generateUniqueKey());
        orderDAO.setMemberEmail(memberEmail);
        orderDAO.setMemberAddress(addressLinkJPA.findByUserIdAndStatus
                        (memberEmail, AddressStatus.DEFAULT.getCode())
                        .get(0).getAddressName());
        orderDAO.setRestaurantId(restaurantId);
        orderDAO.setAmount(totalAmount);
        orderDAO.setDiscount(discount);
        orderDAO.setOrderStatus(OrderStatus.TOPAY.getCode());
        orderDAO.setCreateTime(new Date());
        orderJPA.save(orderDAO);

        for (OrderDetailVO orderDetailVO: orderDetailVOList) {
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            orderDetailDAO.setOrderId(orderDAO.getId());
            orderDetailDAO.setFoodId(orderDetailVO.getFoodId());
            orderDetailDAO.setFoodNum(orderDetailVO.getFoodNum());
            orderDetailDAO.setPrice(orderDetailVO.getPrice());
            orderDetailJPA.save(orderDetailDAO);
        }

        return generateAlertMessage(
                restaurantJPA.getOne(restaurantId).getAddress(),
                orderDAO.getMemberAddress());
    }

    @Override
    public List<OrderVO> findMemberOrders(String email, Integer orderStatus) {
        return generateVOFromOrderDAO(orderJPA
                .findByMemberEmailAndOrderStatus(email, orderStatus));
    }

    @Override
    public List<OrderVO> findRestaurantOrders(String restaurantId, Integer orderStatus) {
        return generateVOFromOrderDAO(orderJPA
                .findByRestaurantIdAndOrderStatus(restaurantId, orderStatus));
    }

    @Override
    public OrderInfoVO getOrderInfo(String orderId) {
        OrderInfoVO orderInfo = new OrderInfoVO();
        OrderDAO orderDAO = orderJPA.getOne(orderId);
        orderInfo.setId(orderId);
        orderInfo.setCreateTime(TimeUtil.timeFormat(orderDAO.getCreateTime()));
        orderInfo.setOrderStatus(OrderStatus.getDescription(orderDAO.getOrderStatus()));
        orderInfo.setRestaurantName(restaurantJPA.getOne(orderDAO.getRestaurantId()).getName());
        orderInfo.setMemberAddress(orderDAO.getMemberAddress());
        orderInfo.setMemberName(memberJPA.getOne(orderDAO.getMemberEmail()).getName());
        orderInfo.setMemberPhone(memberJPA.getOne(orderDAO.getMemberEmail()).getPhone());
        orderInfo.setTotalAmount(orderDAO.getAmount());
        orderInfo.setDiscount(orderDAO.getDiscount());

        List<OrderDetailVO> orderDetails = new ArrayList<>();
        for (OrderDetailDAO orderDetailDAO: orderDetailJPA.findByOrderId(orderId)) {
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            orderDetailDAO.setOrderId(orderId);
            orderDetailVO.setFoodName(foodJPA.findDistinctById(orderDetailDAO.getFoodId()).getName());
            orderDetailVO.setFoodNum(orderDetailDAO.getFoodNum());
            orderDetailVO.setPrice(orderDetailDAO.getPrice());
            orderDetails.add(orderDetailVO);
        }
        orderInfo.setOrderDetails(orderDetails);

        return orderInfo;
    }

    @Override
    public List<OrderVO> payOrder(String id) {
        OrderDAO orderDAO = orderJPA.getOne(id);
        MemberDAO memberDAO = memberJPA.getOne(orderDAO.getMemberEmail());
        if (memberDAO.getAmount() < orderDAO.getAmount()) {
            throw new MemberAmountException();
        }

        for (OrderDetailDAO orderDetail: orderDetailJPA.findByOrderId(id)) {
            FoodDAO foodDAO = foodJPA.findDistinctById(orderDetail.getFoodId());
            if (foodDAO.getNumber() < orderDetail.getFoodNum()) {
                throw new FoodInsufficientException();
            }
        }
        for (OrderDetailDAO orderDetail: orderDetailJPA.findByOrderId(id)) {
            FoodDAO foodDAO = foodJPA.findDistinctById(orderDetail.getFoodId());
            foodDAO.setNumber(foodDAO.getNumber() - orderDetail.getFoodNum());
            foodJPA.save(foodDAO);
        }

        Double moneyToRestaurant = orderDAO.getAmount() * 0.4;
        Double moneyToYummy = orderDAO.getAmount() - moneyToRestaurant - orderDAO.getDiscount();

        memberDAO.setAmount(memberDAO.getAmount() - (moneyToRestaurant + moneyToYummy));
        memberJPA.save(memberDAO);

        RestaurantDAO restaurantDAO = restaurantJPA.getOne(orderDAO.getRestaurantId());
        restaurantDAO.setAmount(restaurantDAO.getAmount() + moneyToRestaurant);
        restaurantJPA.save(restaurantDAO);

        YummyFinanceDAO financeDAO = new YummyFinanceDAO();
        financeDAO.setIncome(moneyToYummy);
        financeDAO.setOrderId(orderDAO.getId());
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
        OrderDAO orderDAO = orderJPA.getOne(id);
        Double moneyToRestaurant = orderDAO.getAmount() * 0.5;

        RestaurantDAO restaurantDAO = restaurantJPA.getOne(orderDAO.getRestaurantId());
        restaurantDAO.setAmount(restaurantDAO.getAmount() + moneyToRestaurant);
        restaurantJPA.save(restaurantDAO);

        YummyFinanceDAO financeDAO = new YummyFinanceDAO();
        financeDAO.setIncome(-moneyToRestaurant);
        financeDAO.setOrderId(orderDAO.getId());
        financeDAO.setTime(new Date());
        yummyFinanceJPA.save(financeDAO);

        return updateOrder(id, OrderStatus.FINISHED.getCode(), true);
    }

    @Override
    public List<OrderVO> cancelOrder(String id) {
        OrderDAO orderDAO = orderJPA.getOne(id);
        if (!orderDAO.getOrderStatus().equals(OrderStatus.TOPAY.getCode()) ||
                !orderDAO.getOrderStatus().equals(OrderStatus.CANCELLED.getCode())) {
            Double moneyBack = orderDAO.getAmount() * 0.5;

            MemberDAO memberDAO = memberJPA.getOne(orderDAO.getMemberEmail());
            memberDAO.setAmount(memberDAO.getAmount() + moneyBack);
            memberJPA.save(memberDAO);

            YummyFinanceDAO financeDAO = new YummyFinanceDAO();
            financeDAO.setIncome(-moneyBack);
            financeDAO.setOrderId(orderDAO.getId());
            financeDAO.setTime(new Date());
            yummyFinanceJPA.save(financeDAO);

            for (OrderDetailDAO orderDetail: orderDetailJPA.findByOrderId(id)) {
                FoodDAO foodDAO = foodJPA.findDistinctById(orderDetail.getFoodId());
                foodDAO.setNumber(foodDAO.getNumber() + orderDetail.getFoodNum());
                foodJPA.save(foodDAO);
            }
        }
        return updateOrder(id, OrderStatus.CANCELLED.getCode(), true);
    }

    private List<OrderVO> updateOrder(String id, Integer newStatus, boolean isMemberEditing) {
        OrderDAO orderDAO = orderJPA.getOne(id);
        Integer oldStatus = orderDAO.getOrderStatus();
        orderDAO.setOrderStatus(newStatus);
        orderJPA.save(orderDAO);
        memberService.updateMemberLevel(orderDAO.getMemberEmail());
        return isMemberEditing ? findMemberOrders(id, oldStatus) : findRestaurantOrders(id, oldStatus);
    }

    private List<OrderVO> generateVOFromOrderDAO(List<OrderDAO> orderDAOList) {
        List<OrderVO> result = new ArrayList<>();
        for (OrderDAO orderDAO: orderDAOList) {
            OrderVO orderVO = new OrderVO();
            orderVO.setId(orderDAO.getId());
            orderVO.setRestaurantName(restaurantJPA.getOne(orderDAO.getRestaurantId()).getName());
            orderVO.setTime(TimeUtil.timeFormat(orderDAO.getCreateTime()));
            orderVO.setActualAmount(orderDAO.getAmount() - orderDAO.getDiscount());
            orderVO.setStatus(OrderStatus.getDescription(orderDAO.getOrderStatus()));
            result.add(orderVO);
        }
        return result;
    }

    private String generateAlertMessage(String restaurantAddressName, String memberAddressName) {
        AddressDAO restaurantAddress = addressJPA.getOne(restaurantAddressName);
        AddressDAO memberAddress = addressJPA.getOne(memberAddressName);
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
