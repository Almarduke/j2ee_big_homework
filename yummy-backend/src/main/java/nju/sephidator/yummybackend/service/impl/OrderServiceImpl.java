package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.enums.AddressStatus;
import nju.sephidator.yummybackend.enums.OrderStatus;
import nju.sephidator.yummybackend.model.OrderDAO;
import nju.sephidator.yummybackend.model.OrderDetailDAO;
import nju.sephidator.yummybackend.repository.*;
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

    @Override
    public void submit(String restaurantId, String memberEmail, Double totalAmount, List<OrderDetailVO> orderDetailVOList) {
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.setId(KeyUtil.generateUniqueKey());
        orderDAO.setMemberEmail(memberEmail);
        orderDAO.setMemberAddress(addressLinkJPA.findByUserIdAndStatus
                        (memberEmail, AddressStatus.DEFAULT.getCode())
                        .get(0).getAddressName());
        orderDAO.setRestaurantId(restaurantId);
        orderDAO.setAmount(totalAmount);
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
    public List<OrderVO> updateOrder(String id, boolean isMemberEditing) {
        OrderDAO orderDAO = orderJPA.getOne(id);
        Integer oldStatus = orderDAO.getOrderStatus();
        orderDAO.updateStatus();
        orderJPA.save(orderDAO);
        return isMemberEditing ? findMemberOrders(id, oldStatus) : findRestaurantOrders(id, oldStatus);
    }

    private List<OrderVO> generateVOFromOrderDAO(List<OrderDAO> orderDAOList) {
        List<OrderVO> result = new ArrayList<>();
        for (OrderDAO orderDAO: orderDAOList) {
            OrderVO orderVO = new OrderVO();
            orderVO.setId(orderDAO.getId());
            orderVO.setRestaurantName(restaurantJPA.getOne(orderDAO.getRestaurantId()).getName());
            orderVO.setTime(TimeUtil.timeFormat(orderDAO.getCreateTime()));
            orderVO.setAmount(orderDAO.getAmount());
            orderVO.setStatus(OrderStatus.getDescription(orderDAO.getOrderStatus()));
            result.add(orderVO);
        }
        return result;
    }
}
