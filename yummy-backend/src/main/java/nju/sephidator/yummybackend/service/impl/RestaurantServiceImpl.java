package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.model.RestaurantDAO;
import nju.sephidator.yummybackend.repository.AddressLinkJPA;
import nju.sephidator.yummybackend.repository.RestaurantJPA;
import nju.sephidator.yummybackend.service.RestaurantService;
import nju.sephidator.yummybackend.vo.RestaurantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantJPA restaurantJPA;

    @Autowired
    AddressLinkJPA addressLinkJPA;

    @Override
    public synchronized String create(RestaurantVO restaurantVO) {
        String id = generateUniqueId();
        RestaurantDAO restaurantDAO = restaurantVO.getRestaurantDAO(id);
        restaurantJPA.save(restaurantDAO);
        addressLinkJPA.save(restaurantVO.getAddressLinkDAO(id));
        return id;
    }

    @Override
    public boolean passwordCorrect(String id, String password) {
        return restaurantJPA.existsByIdAndPassword(id, password);
    }

    @Override
    public List<RestaurantVO> getAll() {
        List<RestaurantDAO> restaurantDAOS = restaurantJPA.findAll();
        List<RestaurantVO> result = new ArrayList<>();
        for (RestaurantDAO restaurantDAO: restaurantDAOS) {
            RestaurantVO restaurant = new RestaurantVO();
            restaurant.setId(restaurantDAO.getId());
            restaurant.setName(restaurantDAO.getName());
            restaurant.setPhone(restaurantDAO.getPhone());
            restaurant.setAddress(addressLinkJPA.findByUserId(restaurantDAO.getId()).get(0).getAddressName());
            result.add(restaurant);
        }
        return result;
    }

    private String generateUniqueId() {
        StringBuilder id = new StringBuilder(String.valueOf(restaurantJPA.count()));
        while (id.length() < 7) { id.insert(0, "0"); }
        return id.toString();
    }
}
