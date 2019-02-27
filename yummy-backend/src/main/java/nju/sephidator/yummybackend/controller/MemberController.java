package nju.sephidator.yummybackend.controller;


import nju.sephidator.yummybackend.dataobject.AddressDAO;
import nju.sephidator.yummybackend.dataobject.MemberDAO;
import nju.sephidator.yummybackend.service.MemberService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.MemberVO;
import nju.sephidator.yummybackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping(value = "/signUp")
    public ResultVO<?> signUp(@RequestBody MemberVO member) {
        if (memberService.emailExists(member.getEmail())) {
            return ResultVOUtil.error(HttpStatus.FORBIDDEN.value(), "邮箱已经注册");
        } else if (memberService.checkCodeError(member.getEmail(), member.getCheckCode())) {
            return ResultVOUtil.error(HttpStatus.FORBIDDEN.value(), "验证码错误");
        } else {
            memberService.create(member);
            return ResultVOUtil.success("注册成功");
        }
    }

    @PostMapping(value = "/login")
    public ResultVO<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {

    }

    @PostMapping(value = "/sendCheckCode/{email}")
    public ResultVO<?> sendCheckCode(@PathVariable("email") String email) {
        boolean success = memberService.sendCheckCode(email);
        return success ? ResultVOUtil.success("验证码已发送") :
                ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "服务器错误，发送邮件失败");
    }
}
