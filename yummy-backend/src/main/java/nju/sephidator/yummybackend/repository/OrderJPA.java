package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.OrderDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJPA extends JpaRepository<OrderDAO, String> {

    List<OrderDAO> findByMemberEmailAndOrderStatus(String email, Integer status);

    List<OrderDAO> findByRestaurantIdAndOrderStatus(String restaurantId, Integer status);
}
