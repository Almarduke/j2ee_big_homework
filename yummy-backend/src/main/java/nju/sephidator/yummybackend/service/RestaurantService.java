package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.model.RestaurantDAO;
import nju.sephidator.yummybackend.vo.RestaurantVO;

import java.util.List;

public interface RestaurantService {

    String create(RestaurantVO restaurantVO);

    boolean passwordCorrect(String id, String password);

    List<RestaurantVO> getAll();

    RestaurantVO getById(String id);
}
