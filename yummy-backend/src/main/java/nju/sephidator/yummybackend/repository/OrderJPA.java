package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJPA extends JpaRepository<Order, String> {

    List<Order> findByMemberEmailAndOrderStatus(String email, Integer status);

    List<Order> findByRestaurantIdAndOrderStatus(String restaurantId, Integer status);
}
