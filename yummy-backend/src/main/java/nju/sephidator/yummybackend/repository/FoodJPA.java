package nju.sephidator.yummybackend.repository;


import nju.sephidator.yummybackend.model.FoodDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodJPA extends JpaRepository<FoodDAO, String> {
    List<FoodDAO> findByRestaurantIdAndAvailable(String restaurantId, boolean available);

    FoodDAO findDistinctById(Integer id);
}
