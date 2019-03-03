package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.vo.restaurant.RestaurantInfoVO;
import nju.sephidator.yummybackend.vo.restaurant.RestaurantSignUpVO;

import java.util.List;

public interface RestaurantService {

    String create(RestaurantSignUpVO restaurantVO);

    boolean passwordCorrect(String id, String password);

    List<RestaurantInfoVO> getAll();

    RestaurantInfoVO getRestaurantInfo(String id);

    void updateRestaurantInfo(String restaurantId, String newName, String newPhone, String newAddress);

    RestaurantInfoVO withdrawMoney(String restaurantId, Double amount);
}
