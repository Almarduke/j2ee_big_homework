package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.model.FoodDAO;
import nju.sephidator.yummybackend.repository.FoodJPA;
import nju.sephidator.yummybackend.service.FoodService;
import nju.sephidator.yummybackend.vo.FoodVO;
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
        FoodDAO foodDAO = foodVO.generateFoodDAO(true);
        foodJPA.save(foodDAO);
        return getByRestaurantId(foodDAO.getRestaurantId());
    }

    @Override
    public List<FoodVO> deleteFood(FoodVO foodVO) {
        FoodDAO foodDAO = foodVO.generateFoodDAO(false);
        foodJPA.save(foodDAO);
        return getByRestaurantId(foodDAO.getRestaurantId());
    }

    @Override
    public List<FoodVO> getByRestaurantId(String restaurantId) {
        return foodJPA.findByRestaurantIdAndAvailable
                (restaurantId, true).stream().map(FoodVO::new)
                .collect(Collectors.toList());
    }
}
