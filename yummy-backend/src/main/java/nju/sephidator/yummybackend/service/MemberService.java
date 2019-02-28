package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.vo.MemberVO;

public interface MemberService {

    boolean emailExists(String email);

    void create(MemberVO memberVO);

    boolean sendCheckCode(String email);

    boolean checkCodeError(String email, String checkCode);

    boolean passwordCorrect(String email, String password);
}
