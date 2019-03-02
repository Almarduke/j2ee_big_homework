package nju.sephidator.yummybackend.vo;

import lombok.Data;

@Data
public class OrderVO {

    private String id;

    private String time;

    private String restaurantName;

    private Double actualAmount;

    private String status;
}
