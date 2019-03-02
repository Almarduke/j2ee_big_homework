package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.model.RestaurantDAO;
import nju.sephidator.yummybackend.repository.AddressLinkJPA;
import nju.sephidator.yummybackend.repository.RestaurantJPA;
import nju.sephidator.yummybackend.service.AddressService;
import nju.sephidator.yummybackend.service.RestaurantService;
import nju.sephidator.yummybackend.vo.RestaurantInfoVO;
import nju.sephidator.yummybackend.vo.RestaurantSignUpVO;
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

    @Autowired
    private AddressService addressService;

    @Override
    public synchronized String create(RestaurantSignUpVO restaurantVO) {
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
    public List<RestaurantSignUpVO> getAll() {
        List<RestaurantDAO> restaurantDAOS = restaurantJPA.findAll();
        List<RestaurantSignUpVO> result = new ArrayList<>();
        for (RestaurantDAO restaurantDAO: restaurantDAOS) {
            result.add(generateRestaurantVO(restaurantDAO));
        }
        return result;
    }

    @Override
    public RestaurantSignUpVO getById(String id) {
        RestaurantDAO restaurantDAO = restaurantJPA.getOne(id);
        return generateRestaurantVO(restaurantDAO);
    }

    @Override
    public RestaurantInfoVO getRestaurantInfo(String id) {
        RestaurantInfoVO restaurantInfoVO = new RestaurantInfoVO();
        restaurantInfoVO.setRestaurantInfo(restaurantJPA.getOne(id));
        restaurantInfoVO.setAddressList(addressService.getUserAddressList(id));
        return restaurantInfoVO;
    }

    private RestaurantSignUpVO generateRestaurantVO(RestaurantDAO restaurantDAO) {
        RestaurantSignUpVO restaurant = new RestaurantSignUpVO();
        restaurant.setId(restaurantDAO.getId());
        restaurant.setName(restaurantDAO.getName());
        restaurant.setPhone(restaurantDAO.getPhone());
        restaurant.setAddress(addressLinkJPA.findByUserId(restaurantDAO.getId()).get(0).getAddressName());
        return restaurant;
    }

    private String generateUniqueId() {
        StringBuilder id = new StringBuilder(String.valueOf(restaurantJPA.count()));
        while (id.length() < 7) { id.insert(0, "0"); }
        return id.toString();
    }
}
