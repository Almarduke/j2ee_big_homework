package nju.sephidator.yummybackend.vo;

import lombok.Data;
import nju.sephidator.yummybackend.model.OrderDetailDAO;

@Data
public class OrderDetailVO {

    private Integer foodId;

    private String foodName;

    private Integer foodNum;

    private Double price;
}
