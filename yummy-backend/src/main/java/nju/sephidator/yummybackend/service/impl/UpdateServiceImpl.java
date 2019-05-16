package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.enums.OrderStatus;
import nju.sephidator.yummybackend.model.Order;
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
        for (Order order :
                orderJPA.findByMemberEmailAndOrderStatus
                        ("sephidator@gmail.com", 0)) {
            updateOrder(order);
        }
    }

    @Override
    public void updateRestaurantOrders(String restaurantId) {
        for (Order order :
                orderJPA.findByMemberEmailAndOrderStatus
                        (restaurantId, OrderStatus.TOPAY.getCode())) {
            updateOrder(order);
        }
    }

    @Override
    public void updateOrder(String orderId) {
        updateOrder(orderJPA.getOne(orderId));
    }

    private void updateOrder(Order order) {
        long currentTime = new Date().getTime();
        long TWO_HOUR = 1000 * 60 * 60 * 2;
        long FIFTEEN_MINUTE = 1000 * 60 * 1;
        if (order.getOrderStatus().equals(OrderStatus.TOPAY.getCode())
                && order.getCreateTime().getTime() < currentTime - FIFTEEN_MINUTE) {
            orderService.cancelOrder(order.getId());
        } else if (order.getOrderStatus().equals(OrderStatus.DISTRIBUTING.getCode())
                && order.getCreateTime().getTime() < currentTime - TWO_HOUR) {
            orderService.finishOrder(order.getId());
        }
    }
}
