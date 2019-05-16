package nju.sephidator.yummybackend.vo.restaurant;

import lombok.Data;
import nju.sephidator.yummybackend.model.Restaurant;

@Data
public class RestaurantSignUpVO {

    private String id;

    private String password;

    private String phone;

    private String name;

    private String address;

    public Restaurant getRestaurantDAO(String id) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName(name);
        restaurant.setPassword(password);
        restaurant.setPhone(phone);
        restaurant.setAddress(address);
        restaurant.setAmount(0.0);
        restaurant.setRevenue(0.0);
        return restaurant;
    }
}
