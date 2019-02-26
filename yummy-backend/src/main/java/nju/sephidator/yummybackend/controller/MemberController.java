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
    public ResultVO<?> signUpWorker(@RequestBody MemberVO member) {
        if (memberService.emailExists(member.getEmail())) {
            return ResultVOUtil.error(HttpStatus.UNAUTHORIZED.value(), "邮箱已经注册");
        } else {
            memberService.create(member);
            return ResultVOUtil.success();
        }
    }

    @PostMapping(value = "/cx")
    public String signUpWorker() {
        return "到达了";
    }

}
