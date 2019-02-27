package nju.sephidator.yummybackend.controller;


import nju.sephidator.yummybackend.service.AddressService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/getAll")
    public ResultVO<?> getAll() {
        List<String> resultList = addressService.getAll();
        return ResultVOUtil.success(resultList, "");
    }

}
