package nju.sephidator.yummybackend.vo;

import java.io.Serializable;

public class LoginUserVO implements Serializable {
    private String id;
    private String token;
    private String type;

    public LoginUserVO(String id, String token, String type) {
        this.id = id;
        this.token = token;
        this.type = type;
    }
}
