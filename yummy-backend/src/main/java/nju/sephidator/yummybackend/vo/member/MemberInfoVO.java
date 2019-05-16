package nju.sephidator.yummybackend.vo.member;

import lombok.Data;
import nju.sephidator.yummybackend.model.Member;
import nju.sephidator.yummybackend.vo.address.AddressVO;

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

    public void setMemberInfo(Member member) {
        this.email = member.getEmail();
        this.phone = member.getPhone();
        this.name = member.getName();
        this.level = member.getLevel();
        this.amount = member.getAmount();
    }
}
