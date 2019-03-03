package nju.sephidator.yummybackend.vo.address;

import lombok.Data;

@Data
public class AddressVO {

    private String name;

    private boolean isDefault;

    public AddressVO() {}

    public AddressVO(String name, boolean isDefault) {
        this.name = name;
        this.isDefault = isDefault;
    }
}
