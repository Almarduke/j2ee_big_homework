package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.enums.OrderStatus;
import nju.sephidator.yummybackend.model.OrderDAO;
import nju.sephidator.yummybackend.repository.OrderJPA;
import nju.sephidator.yummybackend.service.OrderService;
import nju.sephidator.yummybackend.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private OrderJPA orderJPA;

    @Autowired
    private OrderService orderService;

    @Override
    public void updateMemberOrders(String email) {
        for (OrderDAO orderDAO:
                orderJPA.findByMemberEmailAndOrderStatus
                        (email, OrderStatus.TOPAY.getCode())) {
            updateOrder(orderDAO);
        }
    }

    @Override
    public void updateRestaurantOrders(String restaurantId) {
        for (OrderDAO orderDAO:
                orderJPA.findByMemberEmailAndOrderStatus
                        (restaurantId, OrderStatus.TOPAY.getCode())) {
            updateOrder(orderDAO);
        }
    }

    @Override
    public void updateOrder(String orderId) {
        updateOrder(orderJPA.getOne(orderId));
    }

    private void updateOrder(OrderDAO orderDAO) {
        long currentTime = new Date().getTime();
        long TWO_HOUR = 1000 * 60 * 60 * 2;
        long FIFTEEN_MINUTE = 1000 * 60 * 15;
        if (orderDAO.getOrderStatus().equals(OrderStatus.TOPAY.getCode())
                && orderDAO.getCreateTime().getTime() < currentTime - FIFTEEN_MINUTE) {
            orderService.cancelOrder(orderDAO.getId());
        } else if (orderDAO.getOrderStatus().equals(OrderStatus.DISTRIBUTING.getCode())
                && orderDAO.getCreateTime().getTime() < currentTime - TWO_HOUR) {
            orderService.finishOrder(orderDAO.getId());
        }
    }
}
