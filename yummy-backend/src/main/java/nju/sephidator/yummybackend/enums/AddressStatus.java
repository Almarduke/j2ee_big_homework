package nju.sephidator.yummybackend.enums;

import lombok.Getter;

@Getter
public enum AddressStatus {
    DEFAULT(0, "默认"),
    COMMON(1, "普通"),
    ;

    private Integer code;

    private String message;

    AddressStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
