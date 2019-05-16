package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.model.Food;
import nju.sephidator.yummybackend.repository.FoodJPA;
import nju.sephidator.yummybackend.service.FoodService;
import nju.sephidator.yummybackend.vo.food.FoodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodJPA foodJPA;

    @Override
    public List<FoodVO> saveFood(FoodVO foodVO) {
        Food food = foodVO.generateFoodDAO(true);
        foodJPA.save(food);
        return getByRestaurantId(food.getRestaurantId());
    }

    @Override
    public List<FoodVO> deleteFood(FoodVO foodVO) {
        Food food = foodVO.generateFoodDAO(false);
        foodJPA.save(food);
        return getByRestaurantId(food.getRestaurantId());
    }

    @Override
    public List<FoodVO> getByRestaurantId(String restaurantId) {
        return foodJPA.findByRestaurantIdAndAvailable
                (restaurantId, true).stream().map(FoodVO::new)
                .collect(Collectors.toList());
    }
}
