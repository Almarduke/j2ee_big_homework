package nju.sephidator.yummybackend.vo.restaurant;

import lombok.Data;
import nju.sephidator.yummybackend.model.RestaurantDAO;
import nju.sephidator.yummybackend.model.RestaurantInfoCheckDAO;

import java.io.Serializable;

@Data
public class RestaurantInfoCheckVO implements Serializable {

    private String id;

    private String phone;

    private String name;

    private String address;

    public RestaurantInfoCheckVO() {
    }

    public RestaurantInfoCheckVO(RestaurantInfoCheckDAO restaurantInfoCheckDAO) {
        this.id = restaurantInfoCheckDAO.getId();
        this.phone = restaurantInfoCheckDAO.getPhone();
        this.name = restaurantInfoCheckDAO.getName();
        this.address = restaurantInfoCheckDAO.getAddress();
    }
}
