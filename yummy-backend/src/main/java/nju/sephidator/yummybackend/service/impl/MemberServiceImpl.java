package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.enums.AddressStatus;
import nju.sephidator.yummybackend.model.AddressLinkDAO;
import nju.sephidator.yummybackend.model.CheckCodeDAO;
import nju.sephidator.yummybackend.model.MemberDAO;
import nju.sephidator.yummybackend.repository.AddressLinkJPA;
import nju.sephidator.yummybackend.repository.CheckCodeJPA;
import nju.sephidator.yummybackend.repository.MemberJPA;
import nju.sephidator.yummybackend.service.AddressService;
import nju.sephidator.yummybackend.service.MemberService;
import nju.sephidator.yummybackend.utils.KeyUtil;
import nju.sephidator.yummybackend.vo.AddressVO;
import nju.sephidator.yummybackend.vo.MemberInfoVO;
import nju.sephidator.yummybackend.vo.MemberSignUpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberJPA memberJPA;

    @Autowired
    private AddressLinkJPA addressLinkJPA;

    @Autowired
    private CheckCodeJPA checkCodeJPA;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private AddressService addressService;

    @Override
    public boolean emailExists(String email) {
        return memberJPA.existsById(email);
    }

    @Override
    public void create(MemberSignUpVO memberSignUpVO) {
        memberJPA.save(memberSignUpVO.getMemberDAO());
        addressLinkJPA.save(memberSignUpVO.getAddressLinkDAO());
    }

    @Override
    public boolean sendCheckCode(String email) {
        String code;
        if (checkCodeJPA.existsById(email)) {
            code = checkCodeJPA.getOne(email).getCode();
        } else {
            code = KeyUtil.randomCheckCode();
            CheckCodeDAO checkCodeDAO = new CheckCodeDAO();
            checkCodeDAO.setEmail(email);
            checkCodeDAO.setCode(code);
            checkCodeJPA.save(checkCodeDAO);
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("2683514831@qq.com");
        simpleMailMessage.setSubject("Yummy邮件验证码");
        simpleMailMessage.setText("您的验证码是：" + code);

        try {
            System.out.println("开始发送邮件验证码");
            System.out.println("目标邮箱是：" + email);
            mailSender.send(simpleMailMessage);
            System.out.println("邮件验证码发送结束");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkCodeError(String email, String checkCode) {
        return !checkCodeJPA.existsByEmailAndCode(email, checkCode);
    }

    @Override
    public boolean passwordCorrect(String email, String password) {
        return memberJPA.existsByEmailAndPassword(email, password);
    }

    @Override
    public MemberInfoVO getMemberInfo(String email) {
        MemberInfoVO memberInfoVO = new MemberInfoVO();
        memberInfoVO.setMemberInfo(memberJPA.getOne(email));
        memberInfoVO.setAddressList(addressService.getUserAddressList(email));
        return memberInfoVO;
    }
}
