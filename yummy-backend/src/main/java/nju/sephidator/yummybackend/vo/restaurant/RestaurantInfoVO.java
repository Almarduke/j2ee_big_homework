package nju.sephidator.yummybackend.vo.restaurant;

import lombok.Data;
import nju.sephidator.yummybackend.model.Restaurant;

import java.io.Serializable;

@Data
public class RestaurantInfoVO implements Serializable {

    private String id;

    private String phone;

    private String name;

    private String address;

    private Double amount;

    public RestaurantInfoVO() {}

    public RestaurantInfoVO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.phone = restaurant.getPhone();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.amount = restaurant.getAmount();
    }
}
