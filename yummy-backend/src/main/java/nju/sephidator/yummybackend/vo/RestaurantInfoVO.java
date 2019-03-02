package nju.sephidator.yummybackend.vo;

import lombok.Data;
import nju.sephidator.yummybackend.model.MemberDAO;
import nju.sephidator.yummybackend.model.RestaurantDAO;

import java.io.Serializable;
import java.util.List;

@Data
public class RestaurantInfoVO implements Serializable {

    private String id;

    private String phone;

    private String name;

    private String address;

    private Double amount;

    public void setRestaurantInfo(RestaurantDAO restaurantDAO) {
        this.id = restaurantDAO.getId();
        this.phone = restaurantDAO.getPhone();
        this.name = restaurantDAO.getName();
        this.address = restaurantDAO.getAddress();
        this.amount = restaurantDAO.getAmount();
    }
}
