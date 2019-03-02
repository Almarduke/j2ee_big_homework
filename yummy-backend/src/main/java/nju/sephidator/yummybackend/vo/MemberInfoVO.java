package nju.sephidator.yummybackend.vo;

import lombok.Data;
import nju.sephidator.yummybackend.enums.AddressStatus;
import nju.sephidator.yummybackend.model.MemberDAO;

import java.io.Serializable;
import java.util.List;

@Data
public class MemberInfoVO implements Serializable {

    private String email;

    private String phone;

    private String name;

    private Integer level;

    private Double amount;

    private List<AddressVO> addressList;

    public void setMemberInfo(MemberDAO memberDAO) {
        this.email = memberDAO.getEmail();
        this.phone = memberDAO.getPhone();
        this.name = memberDAO.getName();
        this.level = memberDAO.getLevel();
        this.amount = memberDAO.getAmount();
    }
}
