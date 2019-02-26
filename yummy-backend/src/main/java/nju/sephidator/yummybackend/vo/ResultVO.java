package nju.sephidator.yummybackend.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 3657651453120568565L;

    private Integer code;

    private String msg;

    private T data;
}
