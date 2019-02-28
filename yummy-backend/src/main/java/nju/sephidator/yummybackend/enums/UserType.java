package nju.sephidator.yummybackend.enums;

import lombok.Getter;

@Getter
public enum UserType {
    ADMIN("ADMIN"),
    MEMBER("MEMBER"),
    RESTAURANT("RESTAURANT")
    ;

    private String value = "";

    UserType(String value) {
        this.value = value;
    }
}
