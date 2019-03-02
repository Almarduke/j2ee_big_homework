package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.vo.OrderDetailVO;
import nju.sephidator.yummybackend.vo.OrderInfoVO;
import nju.sephidator.yummybackend.vo.OrderVO;

import java.util.List;

public interface OrderService {

    void submit(String restaurantId, String memberEmail, Double totalAmount, Double discount, List<OrderDetailVO> orderDetailVOList);

    List<OrderVO> findMemberOrders(String email, Integer orderStatus);

    List<OrderVO> findRestaurantOrders(String restaurantId, Integer orderStatus);

    OrderInfoVO getOrderInfo(String id);

    List<OrderVO> payOrder(String id);

    List<OrderVO> handleOrder(String id);

    List<OrderVO> finishOrder(String id);

    List<OrderVO> cancelOrder(String id);
}
