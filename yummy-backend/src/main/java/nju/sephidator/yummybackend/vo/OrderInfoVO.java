package nju.sephidator.yummybackend.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderInfoVO {

    private String id;

    private String createTime;

    private String orderStatus;

    private String restaurantName;

    private String memberName;

    private String memberPhone;

    private String memberAddress;

    private Double totalAmount;

    private List<OrderDetailVO> orderDetails;
}
