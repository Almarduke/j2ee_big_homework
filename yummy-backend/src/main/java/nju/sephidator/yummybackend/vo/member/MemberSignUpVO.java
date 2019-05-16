package nju.sephidator.yummybackend.vo.member;

import lombok.Data;
import nju.sephidator.yummybackend.enums.AddressStatus;
import nju.sephidator.yummybackend.model.AddressLink;
import nju.sephidator.yummybackend.model.Member;

@Data
public class MemberSignUpVO {

    private String email;

    private String password;

    private String phone;

    private String name;

    private String address;

    private String checkCode;

    public Member getMemberDAO() {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setPhone(phone);
        member.setName(name);
        member.setAmount(0.0);
        member.setLevel(1);
        member.setAvailable(true);
        return member;
    }

    public AddressLink getAddressLinkDAO() {
        return new AddressLink(email, address, AddressStatus.DEFAULT.getCode());
    }
}
