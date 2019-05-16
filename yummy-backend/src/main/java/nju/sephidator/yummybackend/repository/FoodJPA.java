package nju.sephidator.yummybackend.repository;


import nju.sephidator.yummybackend.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodJPA extends JpaRepository<Food, String> {
    List<Food> findByRestaurantIdAndAvailable(String restaurantId, boolean available);

    Food findDistinctById(Integer id);
}
