package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.model.MemberDAO;
import nju.sephidator.yummybackend.vo.MemberInfoVO;
import nju.sephidator.yummybackend.vo.MemberSignUpVO;

public interface MemberService {

    boolean emailExists(String email);

    void create(MemberSignUpVO memberSignUpVO);

    boolean sendCheckCode(String email);

    boolean checkCodeError(String email, String checkCode);

    boolean passwordCorrect(String email, String password);

    MemberInfoVO getMemberInfo(String email);
}
