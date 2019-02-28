package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.RestaurantDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantJPA extends JpaRepository<RestaurantDAO, String> {
    boolean existsByIdAndPassword(String id, String password);
}
