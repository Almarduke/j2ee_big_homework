package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.YummyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJPA extends JpaRepository<YummyOrder, String> {

    List<YummyOrder> findByMemberEmailAndOrderStatus(String email, Integer status);

    List<YummyOrder> findByRestaurantIdAndOrderStatus(String restaurantId, Integer status);
}
