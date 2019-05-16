package nju.sephidator.yummybackend.vo.order;

import lombok.Data;

@Data
public class OrderDetailVO {

    private Integer foodId;

    private String foodName;

    private Integer foodNum;

    private Double price;
}
