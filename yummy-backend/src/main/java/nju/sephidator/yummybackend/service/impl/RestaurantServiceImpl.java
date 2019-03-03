package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.exceptions.RestaurantAmountException;
import nju.sephidator.yummybackend.model.RestaurantDAO;
import nju.sephidator.yummybackend.model.RestaurantInfoCheckDAO;
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
        RestaurantDAO restaurantDAO = restaurantSignUpVO.getRestaurantDAO(id);
        restaurantJPA.save(restaurantDAO);
        return id;
    }

    @Override
    public boolean passwordCorrect(String id, String password) {
        return restaurantJPA.existsByIdAndPassword(id, password);
    }

    @Override
    public List<RestaurantInfoVO> getAll() {
        List<RestaurantDAO> restaurantDAOS = restaurantJPA.findAll();
        List<RestaurantInfoVO> result = new ArrayList<>();
        for (RestaurantDAO restaurantDAO: restaurantDAOS) {
            result.add(new RestaurantInfoVO(restaurantDAO));
        }
        return result;
    }

    @Override
    public RestaurantInfoVO getRestaurantInfo(String id) {
        return new RestaurantInfoVO(restaurantJPA.getOne(id));
    }

    @Override
    public void updateRestaurantInfo(String restaurantId, String newName, String newPhone, String newAddress) {
        RestaurantDAO restaurantDAO = restaurantJPA.getOne(restaurantId);
        RestaurantInfoCheckDAO newInfo = new RestaurantInfoCheckDAO();
        newInfo.setId(restaurantDAO.getId());
        newInfo.setName(newName);
        newInfo.setPhone(newPhone);
        newInfo.setAddress(newAddress);
        restaurantInfoCheckJPA.save(newInfo);
    }

    @Override
    public RestaurantInfoVO withdrawMoney(String restaurantId, Double amount) {
        RestaurantDAO restaurantDAO = restaurantJPA.getOne(restaurantId);
        if (restaurantDAO.getAmount() < amount) { throw new RestaurantAmountException(); }
        restaurantDAO.setAmount(restaurantDAO.getAmount() - amount);
        restaurantJPA.save(restaurantDAO);
        return getRestaurantInfo(restaurantId);
    }

    private synchronized String generateUniqueId() {
        StringBuilder id = new StringBuilder(String.valueOf(restaurantJPA.count()));
        while (id.length() < 7) { id.insert(0, "0"); }
        return id.toString();
    }
}
