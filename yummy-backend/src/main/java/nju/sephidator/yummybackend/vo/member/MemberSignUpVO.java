package nju.sephidator.yummybackend.vo.member;

import lombok.Data;
import nju.sephidator.yummybackend.enums.AddressStatus;
import nju.sephidator.yummybackend.model.AddressLinkDAO;
import nju.sephidator.yummybackend.model.MemberDAO;

@Data
public class MemberSignUpVO {

    private String email;

    private String password;

    private String phone;

    private String name;

    private String address;

    private String checkCode;

    public MemberDAO getMemberDAO() {
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.setEmail(email);
        memberDAO.setPassword(password);
        memberDAO.setPhone(phone);
        memberDAO.setName(name);
        memberDAO.setAmount(0.0);
        memberDAO.setLevel(1);
        return memberDAO;
    }

    public AddressLinkDAO getAddressLinkDAO() {
        return new AddressLinkDAO(email, address, AddressStatus.DEFAULT.getCode());
    }
}
