package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.model.RestaurantDAO;
import nju.sephidator.yummybackend.vo.RestaurantVO;

public interface RestaurantService {

    String create(RestaurantVO restaurantVO);

    boolean passwordCorrect(String id, String password);
}
