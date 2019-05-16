package nju.sephidator.yummybackend.service;

public interface UpdateService {

    void updateMemberOrders(String email);

    void updateRestaurantOrders(String restaurantId);

    void updateOrder(String orderId);
}
