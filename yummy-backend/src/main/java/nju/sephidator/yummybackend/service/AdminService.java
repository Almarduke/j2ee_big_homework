package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.vo.restaurant.RestaurantInfoCheckVO;
import nju.sephidator.yummybackend.vo.util.StatisticsDetailVO;

import java.util.List;

public interface AdminService {
    List<RestaurantInfoCheckVO> getRestaurantInfoCheckList();

    void approveRestaurantInfoCheck(String restaurantId);

    List<StatisticsDetailVO> getMemberStatistics();

    List<StatisticsDetailVO> getRestaurantStatistics();

    List<StatisticsDetailVO> getFinanceStatistics();
}
