package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.enums.AddressStatus;
import nju.sephidator.yummybackend.enums.OrderStatus;
import nju.sephidator.yummybackend.exceptions.MemberAmountException;
import nju.sephidator.yummybackend.model.*;
import nju.sephidator.yummybackend.repository.*;
import nju.sephidator.yummybackend.service.MemberService;
import nju.sephidator.yummybackend.service.OrderService;
import nju.sephidator.yummybackend.utils.KeyUtil;
import nju.sephidator.yummybackend.utils.TimeUtil;
import nju.sephidator.yummybackend.vo.OrderDetailVO;
import nju.sephidator.yummybackend.vo.OrderInfoVO;
import nju.sephidator.yummybackend.vo.OrderVO;
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
    public void submit(String restaurantId, String memberEmail, Double totalAmount, Double discount, List<OrderDetailVO> orderDetailVOList) {
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
        orderDAO.setUpdateTime(new Date());
        orderJPA.save(orderDAO);

        for (OrderDetailVO orderDetailVO: orderDetailVOList) {
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            orderDetailDAO.setOrderId(orderDAO.getId());
            orderDetailDAO.setFoodId(orderDetailVO.getFoodId());
            orderDetailDAO.setFoodNum(orderDetailVO.getFoodNum());
            orderDetailDAO.setPrice(orderDetailVO.getPrice());
            orderDetailJPA.save(orderDetailDAO);
        }
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
        Double moneyBack = orderDAO.getAmount() * 0.5;

        MemberDAO memberDAO = memberJPA.getOne(orderDAO.getMemberEmail());
        memberDAO.setAmount(memberDAO.getAmount() + moneyBack);
        memberJPA.save(memberDAO);

        YummyFinanceDAO financeDAO = new YummyFinanceDAO();
        financeDAO.setIncome(-moneyBack);
        financeDAO.setOrderId(orderDAO.getId());
        financeDAO.setTime(new Date());
        yummyFinanceJPA.save(financeDAO);

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
}
