package nju.sephidator.yummybackend.vo;

import lombok.Data;
import nju.sephidator.yummybackend.dataobject.AddressDAO;
import nju.sephidator.yummybackend.dataobject.MemberDAO;

import java.math.BigDecimal;

@Data
public class MemberVO {
    private String email;

    private String password;

    private String phone;

    private String name;

    private Integer coordinateX;

    private Integer coordinateY;

    private String description;

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

    public AddressDAO getAddressDAO() {
        AddressDAO addressDAO = new AddressDAO();
        addressDAO.setUserId(email);
        addressDAO.setCoordinateX(coordinateX);
        addressDAO.setCoordinateY(coordinateY);
        addressDAO.setDescription(description);
        return addressDAO;
    }
}
