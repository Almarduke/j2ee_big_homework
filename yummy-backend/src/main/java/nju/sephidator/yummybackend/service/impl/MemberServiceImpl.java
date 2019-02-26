package nju.sephidator.yummybackend.service.impl;

import lombok.Data;
import nju.sephidator.yummybackend.dataobject.AddressDAO;
import nju.sephidator.yummybackend.dataobject.MemberDAO;
import nju.sephidator.yummybackend.repository.AddressJPA;
import nju.sephidator.yummybackend.repository.MemberJPA;
import nju.sephidator.yummybackend.service.MemberService;
import nju.sephidator.yummybackend.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberJPA memberJPA;

    @Autowired
    private AddressJPA addressJPA;

    @Override
    public boolean emailExists(String email) {
        return memberJPA.existsById(email);
    }

    @Override
    public void create(MemberVO memberVO) {
        memberJPA.save(memberVO.getMemberDAO());
        addressJPA.save(memberVO.getAddressDAO());
    }
}
