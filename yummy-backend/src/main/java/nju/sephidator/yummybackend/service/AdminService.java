package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.vo.restaurant.RestaurantInfoCheckVO;

import java.util.List;

public interface AdminService {
    List<RestaurantInfoCheckVO> getRestaurantInfoCheckList();

    void approveRestaurantInfoCheck(String restaurantId);
}
