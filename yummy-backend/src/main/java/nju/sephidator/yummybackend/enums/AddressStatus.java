package nju.sephidator.yummybackend.enums;

import lombok.Getter;

@Getter
public enum AddressStatus {
    COMMON(0, "普通"),
    DEFAULT(1, "默认"),
    REMOVED(2, "已删除"),
    ;

    private Integer code;

    private String message;

    AddressStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
