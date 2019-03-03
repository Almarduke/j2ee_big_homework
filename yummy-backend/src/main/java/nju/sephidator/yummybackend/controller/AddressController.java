package nju.sephidator.yummybackend.controller;

import nju.sephidator.yummybackend.service.AddressService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping(value = "/addUserAddress")
    public ResultVO<?> addUserAddress(@RequestParam String userId, @RequestParam String addressName) {
        try {
            if (addressService.alreadyExists(userId, addressName)) {
                return ResultVOUtil.error(HttpStatus.FORBIDDEN.value(), "添加地址失败，地址已存在");
            } else {
                return ResultVOUtil.success(addressService.addUserAddressVO(userId, addressName), "添加地址成功");
            }
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，添加地址失败");
        }
    }

    @PostMapping(value = "/setAsDefault")
    public ResultVO<?> setAsDefault(@RequestParam String userId, @RequestParam String addressName) {
        try {
            return ResultVOUtil.success(addressService.setAsDefault(userId, addressName), "更改默认地址成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，添加地址失败");
        }
    }

    @PostMapping(value = "/delete")
    public ResultVO<?> delete(@RequestParam String userId, @RequestParam String addressName) {
        try {
            return ResultVOUtil.success(addressService.delete(userId, addressName), "删除地址成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，添加地址失败");
        }
    }

}
