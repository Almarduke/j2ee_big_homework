package nju.sephidator.yummybackend.controller;


import nju.sephidator.yummybackend.enums.UserType;
import nju.sephidator.yummybackend.service.MemberService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.MemberVO;
import nju.sephidator.yummybackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/sendCheckCode/{email}")
    public ResultVO<?> sendCheckCode(@PathVariable("email") String email) {
        boolean success = memberService.sendCheckCode(email);
        return success ? ResultVOUtil.success("验证码已发送") :
                ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "服务器错误，发送邮件失败");
    }

    @PostMapping(value = "/login")
    public ResultVO<?> signUp(@RequestParam String email, @RequestParam String password) {
        UserType userType;

        if (email.equals("admin@yummy.com")) {
            userType = UserType.ADMIN;
        } else {
            userType = UserType.MEMBER;
        }

        System.out.println("跑起来了");

        if (memberService.passwordCorrect(email, password)) {
            return ResultVOUtil.success(userType.getValue(), "登陆成功");
        } else {
            return ResultVOUtil.error(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误");
        }
    }
}
