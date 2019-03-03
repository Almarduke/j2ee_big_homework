package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.model.OrderDAO;

public interface UpdateService {

    void updateMemberOrders(String email);

    void updateRestaurantOrders(String restaurantId);

    void updateOrder(String orderId);
}
