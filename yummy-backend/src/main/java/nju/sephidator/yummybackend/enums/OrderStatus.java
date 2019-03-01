package nju.sephidator.yummybackend.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    SUBMITTED(0, "已提交"),
    PAYED(1, "已支付"),
    DISTRIBUTING(2, "配送中"),
    FINISHED(3, "已完结"),
    CANCELLED(4, "已取消")
    ;

    private Integer code;

    private String description;

    OrderStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescription(Integer code) {
        switch (code) {
            case 0:
                return SUBMITTED.description;
            case 1:
                return PAYED.description;
            case 2:
                return DISTRIBUTING.description;
            case 3:
                return FINISHED.description;
            case 4:
                return CANCELLED.description;
            default:
                return "订单状态错误";
        }
    }
}
