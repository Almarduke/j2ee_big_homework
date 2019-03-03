package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.model.RestaurantDAO;
import nju.sephidator.yummybackend.model.RestaurantInfoCheckDAO;
import nju.sephidator.yummybackend.repository.RestaurantInfoCheckJPA;
import nju.sephidator.yummybackend.repository.RestaurantJPA;
import nju.sephidator.yummybackend.service.AdminService;
import nju.sephidator.yummybackend.vo.restaurant.RestaurantInfoCheckVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RestaurantInfoCheckJPA restaurantInfoCheckJPA;

    @Autowired
    private RestaurantJPA restaurantJPA;

    @Override
    public List<RestaurantInfoCheckVO> getRestaurantInfoCheckList() {
        List<RestaurantInfoCheckDAO> restaurantInfoCheckDAOList =
                restaurantInfoCheckJPA.findAll();
        return restaurantInfoCheckDAOList.stream()
                .map(RestaurantInfoCheckVO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void approveRestaurantInfoCheck(String restaurantId) {
        RestaurantInfoCheckDAO restaurantInfoCheckDAO =
                restaurantInfoCheckJPA.getOne(restaurantId);
        RestaurantDAO restaurantDAO = restaurantJPA.getOne(restaurantId);
        restaurantDAO.setName(restaurantInfoCheckDAO.getName());
        restaurantDAO.setPhone(restaurantInfoCheckDAO.getPhone());
        restaurantDAO.setAddress(restaurantInfoCheckDAO.getAddress());
        restaurantJPA.save(restaurantDAO);
        restaurantInfoCheckJPA.deleteById(restaurantId);
    }
}
