package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.dataobject.AddressDAO;
import nju.sephidator.yummybackend.dataobject.MemberDAO;
import nju.sephidator.yummybackend.vo.MemberVO;

public interface MemberService {

    boolean emailExists(String email);

    void create(MemberVO memberVO);
}
