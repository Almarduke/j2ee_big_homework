package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.enums.OrderStatus;
import nju.sephidator.yummybackend.model.YummyOrder;
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
        for (YummyOrder order :
                orderJPA.findByMemberEmailAndOrderStatus
                        (email, OrderStatus.TOPAY.getCode())) {
            updateOrder(order);
        }
    }

    @Override
    public void updateRestaurantOrders(String restaurantId) {
        for (YummyOrder yummyOrder :
                orderJPA.findByMemberEmailAndOrderStatus
                        (restaurantId, OrderStatus.TOPAY.getCode())) {
            updateOrder(yummyOrder);
        }
    }

    @Override
    public void updateOrder(String orderId) {
        updateOrder(orderJPA.getOne(orderId));
    }

    private void updateOrder(YummyOrder yummyOrder) {
        long currentTime = new Date().getTime();
        long TWO_HOUR = 1000 * 60 * 60 * 2;
        long FIFTEEN_MINUTE = 1000 * 60 * 1;
        if (yummyOrder.getOrderStatus().equals(OrderStatus.TOPAY.getCode())
                && yummyOrder.getCreateTime().getTime() < currentTime - FIFTEEN_MINUTE) {
            orderService.cancelOrder(yummyOrder.getId());
        } else if (yummyOrder.getOrderStatus().equals(OrderStatus.DISTRIBUTING.getCode())
                && yummyOrder.getCreateTime().getTime() < currentTime - TWO_HOUR) {
            orderService.finishOrder(yummyOrder.getId());
        }
    }
}
