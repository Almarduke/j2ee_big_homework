package nju.sephidator.yummybackend.vo;

import lombok.Data;
import nju.sephidator.yummybackend.dataobject.AddressLinkDAO;
import nju.sephidator.yummybackend.dataobject.MemberDAO;

@Data
public class MemberVO {
    private String email;

    private String password;

    private String phone;

    private String name;

    private String addressName;

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
        AddressLinkDAO addressLinkDAO = new AddressLinkDAO();
        addressLinkDAO.setAddressName(addressName);
        addressLinkDAO.setUserId(email);
        return addressLinkDAO;
    }
}
