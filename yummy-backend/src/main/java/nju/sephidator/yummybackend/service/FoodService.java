package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.vo.food.FoodVO;

import java.util.List;

public interface FoodService {

    List<FoodVO> saveFood(FoodVO foodVO);  // JPA的save方法适用于add和update

    List<FoodVO> deleteFood(FoodVO foodVO);

    List<FoodVO> getByRestaurantId(String restaurantId);
}
