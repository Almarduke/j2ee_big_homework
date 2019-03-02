package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.vo.MemberInfoVO;
import nju.sephidator.yummybackend.vo.RestaurantInfoVO;
import nju.sephidator.yummybackend.vo.RestaurantSignUpVO;

import java.util.List;

public interface RestaurantService {

    String create(RestaurantSignUpVO restaurantVO);

    boolean passwordCorrect(String id, String password);

    List<RestaurantSignUpVO> getAll();

    RestaurantSignUpVO getById(String id);

    RestaurantInfoVO getRestaurantInfo(String id);

    void updateRestaurantInfo(String restaurantId, String newName, String newPhone, String newAddress);

    RestaurantInfoVO withdrawMoneyMoney(String email, Double amount);
}
