package nju.sephidator.yummybackend.vo.restaurant;

import lombok.Data;
import nju.sephidator.yummybackend.model.RestaurantInfoCheck;

import java.io.Serializable;

@Data
public class RestaurantInfoCheckVO implements Serializable {

    private String id;

    private String phone;

    private String name;

    private String address;

    public RestaurantInfoCheckVO() {
    }

    public RestaurantInfoCheckVO(RestaurantInfoCheck restaurantInfoCheck) {
        this.id = restaurantInfoCheck.getId();
        this.phone = restaurantInfoCheck.getPhone();
        this.name = restaurantInfoCheck.getName();
        this.address = restaurantInfoCheck.getAddress();
    }
}
