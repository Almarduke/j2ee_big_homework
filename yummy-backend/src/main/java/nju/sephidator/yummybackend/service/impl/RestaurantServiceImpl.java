package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.exceptions.RestaurantAmountException;
import nju.sephidator.yummybackend.model.Restaurant;
import nju.sephidator.yummybackend.model.RestaurantInfoCheck;
import nju.sephidator.yummybackend.repository.AddressLinkJPA;
import nju.sephidator.yummybackend.repository.RestaurantInfoCheckJPA;
import nju.sephidator.yummybackend.repository.RestaurantJPA;
import nju.sephidator.yummybackend.service.RestaurantService;
import nju.sephidator.yummybackend.vo.restaurant.RestaurantInfoVO;
;
import nju.sephidator.yummybackend.vo.restaurant.RestaurantSignUpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantJPA restaurantJPA;

    @Autowired
    private AddressLinkJPA addressLinkJPA;

    @Autowired
    private RestaurantInfoCheckJPA restaurantInfoCheckJPA;

    @Override
    public synchronized String create(RestaurantSignUpVO restaurantSignUpVO) {
        String id = generateUniqueId();
        Restaurant restaurant = restaurantSignUpVO.getRestaurantDAO(id);
        restaurantJPA.save(restaurant);
        return id;
    }

    @Override
    public boolean passwordCorrect(String id, String password) {
        return restaurantJPA.existsByIdAndPassword(id, password);
    }

    @Override
    public List<RestaurantInfoVO> getAll() {
        List<Restaurant> restaurants = restaurantJPA.findAll();
        List<RestaurantInfoVO> result = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            result.add(new RestaurantInfoVO(restaurant));
        }
        return result;
    }

    @Override
    public RestaurantInfoVO getRestaurantInfo(String id) {
        return new RestaurantInfoVO(restaurantJPA.getOne(id));
    }

    @Override
    public void updateRestaurantInfo(String restaurantId, String newName, String newPhone, String newAddress) {
        Restaurant restaurant = restaurantJPA.getOne(restaurantId);
        RestaurantInfoCheck newInfo = new RestaurantInfoCheck();
        newInfo.setId(restaurant.getId());
        newInfo.setName(newName);
        newInfo.setPhone(newPhone);
        newInfo.setAddress(newAddress);
        restaurantInfoCheckJPA.save(newInfo);
    }

    @Override
    public RestaurantInfoVO withdrawMoney(String restaurantId, Double amount) {
        Restaurant restaurant = restaurantJPA.getOne(restaurantId);
        if (restaurant.getAmount() < amount) { throw new RestaurantAmountException(); }
        restaurant.setAmount(restaurant.getAmount() - amount);
        restaurantJPA.save(restaurant);
        return getRestaurantInfo(restaurantId);
    }

    private synchronized String generateUniqueId() {
        StringBuilder id = new StringBuilder(String.valueOf(restaurantJPA.count()));
        while (id.length() < 7) { id.insert(0, "0"); }
        return id.toString();
    }
}
