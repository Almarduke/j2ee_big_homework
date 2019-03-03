package nju.sephidator.yummybackend.controller;

import nju.sephidator.yummybackend.service.FoodService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.food.FoodVO;
import nju.sephidator.yummybackend.vo.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping(value = "/addFood")
    public ResultVO<?> addFood(@RequestBody FoodVO foodVO) {
        try {
            return ResultVOUtil.success(foodService.saveFood(foodVO), "添加菜品成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，添加菜品失败");
        }
    }

    @PostMapping(value = "/editFood")
    public ResultVO<?> editFood(@RequestBody FoodVO foodVO) {
        try {
            return ResultVOUtil.success(foodService.saveFood(foodVO), "更新菜品成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，更新菜品失败");
        }
    }

    @PostMapping(value = "/deleteFood")
    public ResultVO<?> deleteFood(@RequestBody FoodVO foodVO) {
        try {
            return ResultVOUtil.success(foodService.deleteFood(foodVO), "删除菜品成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，删除菜品失败");
        }
    }

    @GetMapping(value = "/getAll/{restaurantId}")
    public ResultVO<?> getAll(@PathVariable String restaurantId) {
        return ResultVOUtil.success(foodService.getByRestaurantId(restaurantId), "");
    }
}
