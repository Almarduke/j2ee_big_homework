package nju.sephidator.yummybackend.controller;


import nju.sephidator.yummybackend.exceptions.RestaurantAmountException;
import nju.sephidator.yummybackend.service.RestaurantService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.restaurant.RestaurantInfoVO;
import nju.sephidator.yummybackend.vo.restaurant.RestaurantSignUpVO;
import nju.sephidator.yummybackend.vo.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(value = "/signUp")
    public ResultVO<?> signUp(@RequestBody RestaurantSignUpVO restaurant) {
        try {
            String id = restaurantService.create(restaurant);
            return ResultVOUtil.success("创建饭店成功，识别号为：" + id);
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，创建饭店失败");
        }
    }

    @PostMapping(value = "/login")
    public ResultVO<?> login(@RequestParam String id, @RequestParam String password) {
        if (restaurantService.passwordCorrect(id, password)) {
            return ResultVOUtil.success(restaurantService.getById(id), "登陆成功");
        } else {
            return ResultVOUtil.error(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误");
        }
    }

    @GetMapping(value = "/getRestaurantInfo/{id}")
    public ResultVO<?> getRestaurantInfo(@PathVariable String id) {
        try {
            return ResultVOUtil.success(restaurantService.getRestaurantInfo(id), "");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，查询饭店信息失败");
        }
    }

    @GetMapping(value = "getAll")
    public ResultVO<?> getAll() {
        List<RestaurantInfoVO> resultList = restaurantService.getAll();
        return ResultVOUtil.success(resultList, "");
    }

    @PostMapping(value = "/updateRestaurantInfo/{restaurantId}")
    public ResultVO<?> updateMemberInfo(@PathVariable String restaurantId, @RequestParam String newName, @RequestParam String newPhone, @RequestParam String newAddress) {
        try {
            restaurantService.updateRestaurantInfo(restaurantId, newName, newPhone, newAddress);
            return ResultVOUtil.success("", "更新饭店信息提交成功，等待审核");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，更新饭店信息提交失败");
        }
    }

    @PostMapping(value = "/withdrawMoney/{restaurantId}")
    public ResultVO<?> withdrawMoney(@PathVariable String restaurantId, @RequestParam Double amount) {
        try {
            return ResultVOUtil.success(restaurantService.withdrawMoney(restaurantId, amount), "提现成功");
        } catch (RestaurantAmountException e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "余额不足，提现失败");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，提现失败");
        }
    }
}
