package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.vo.member.MemberInfoVO;
import nju.sephidator.yummybackend.vo.member.MemberSignUpVO;

public interface MemberService {

    boolean emailExists(String email);

    void create(MemberSignUpVO memberSignUpVO);

    boolean sendCheckCode(String email);

    boolean checkCodeError(String email, String checkCode);

    boolean passwordCorrect(String email, String password);

    boolean memberAvailable(String email);

    MemberInfoVO getMemberInfo(String email);

    MemberInfoVO updateMemberInfo(String email, String newName, String newPhone);

    MemberInfoVO chargeMoney(String email, Double amount);

    void deleteMember(String email);

    void updateMemberLevel(String email);
}
