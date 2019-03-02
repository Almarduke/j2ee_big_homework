package nju.sephidator.yummybackend.controller;


import nju.sephidator.yummybackend.enums.UserType;
import nju.sephidator.yummybackend.service.MemberService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.UserBriefInfoVO;
import nju.sephidator.yummybackend.vo.MemberSignUpVO;
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
    public ResultVO<?> signUp(@RequestBody MemberSignUpVO member) {
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
    public ResultVO<?> login(@RequestParam String email, @RequestParam String password) {
        UserBriefInfoVO userBriefInfoVO = new UserBriefInfoVO();
        userBriefInfoVO.setId(email);
        userBriefInfoVO.setLevel(memberService.getMemberInfo(email).getLevel());

        if (email.equals("admin@yummy.com")) {
            userBriefInfoVO.setType(UserType.ADMIN.getValue());
        } else {
            userBriefInfoVO.setType(UserType.MEMBER.getValue());
        }

        if (!memberService.memberAvailable(email)) {
            return ResultVOUtil.error(HttpStatus.UNAUTHORIZED.value(), "用户已注销");
        } else if (!memberService.passwordCorrect(email, password)) {
            return ResultVOUtil.error(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误");
        } else {
            return ResultVOUtil.success(userBriefInfoVO, "登陆成功");
        }
    }

    @GetMapping(value = "/getMemberInfo/{email}")
    public ResultVO<?> getMemberInfo(@PathVariable String email) {
        try {
            return ResultVOUtil.success(memberService.getMemberInfo(email), "");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，查询用户信息失败");
        }
    }

    @PostMapping(value = "/updateMemberInfo/{email}")
    public ResultVO<?> updateMemberInfo(@PathVariable String email, @RequestParam String newName, @RequestParam String newPhone) {
        try {
            return ResultVOUtil.success(memberService.updateMemberInfo(email, newName, newPhone), "更新用户信息成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，更新用户信息失败");
        }
    }

    @PostMapping(value = "/chargeMoney/{email}")
    public ResultVO<?> chargeMoney(@PathVariable String email, @RequestParam Double amount) {
        try {
            return ResultVOUtil.success(memberService.chargeMoney(email, amount), "更新用户信息成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，更新用户信息失败");
        }
    }

    @PostMapping(value = "/deleteMember/{email}")
    public ResultVO<?> chargeMoney(@PathVariable String email) {
        try {
            memberService.deleteMember(email);
            return ResultVOUtil.success("", "删除用户成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，删除用户失败");
        }
    }
}
