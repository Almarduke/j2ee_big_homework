package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantJPA extends JpaRepository<Restaurant, String> {
    boolean existsByIdAndPassword(String id, String password);
}
