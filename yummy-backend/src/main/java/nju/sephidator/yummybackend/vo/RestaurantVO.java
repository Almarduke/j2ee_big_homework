package nju.sephidator.yummybackend.vo;

import lombok.Data;
import nju.sephidator.yummybackend.model.AddressLinkDAO;
import nju.sephidator.yummybackend.model.MemberDAO;
import nju.sephidator.yummybackend.model.RestaurantDAO;

@Data
public class RestaurantVO {

    private String password;

    private String phone;

    private String name;

    private String address;

    public RestaurantDAO getRestaurantDAO(String id) {
        RestaurantDAO restaurantDAO = new RestaurantDAO();
        restaurantDAO.setId(id);
        restaurantDAO.setName(name);
        restaurantDAO.setPassword(password);
        restaurantDAO.setPhone(phone);
        restaurantDAO.setAmount(0.0);
        return restaurantDAO;
    }

    public AddressLinkDAO getAddressLinkDAO(String id) {
        AddressLinkDAO addressLinkDAO = new AddressLinkDAO();
        addressLinkDAO.setAddressName(address);
        addressLinkDAO.setUserId(id);
        return addressLinkDAO;
    }
}
