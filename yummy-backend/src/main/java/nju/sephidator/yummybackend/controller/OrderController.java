package nju.sephidator.yummybackend.controller;

import nju.sephidator.yummybackend.service.OrderService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.FoodVO;
import nju.sephidator.yummybackend.vo.OrderDetailVO;
import nju.sephidator.yummybackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/submit")
    public ResultVO<?> submit(@RequestParam String restaurantId,
                              @RequestParam String memberEmail,
                              @RequestParam Double totalAmount,
                              @RequestBody List<OrderDetailVO> orderDetails) {
        try {
            orderService.submit(restaurantId, memberEmail, totalAmount, orderDetails);
            return ResultVOUtil.success("", "提交订单成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，提交订单失败");
        }
    }

    @GetMapping(value = "/getMemberOrders")
    public ResultVO<?> getMemberOrders(@RequestParam String email,
                              @RequestParam Integer orderStatus) {
        try {
            return ResultVOUtil.success(orderService.findMemberOrders(email, orderStatus), "");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，查找订单失败");
        }
    }

    @GetMapping(value = "/getRestaurantOrders")
    public ResultVO<?> getRestaurantOrders(@RequestParam String restaurantId,
                                       @RequestParam Integer orderStatus) {
        try {
            return ResultVOUtil.success(orderService.findRestaurantOrders(restaurantId, orderStatus), "");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，查找订单失败");
        }
    }

    @GetMapping(value = "/getOrderInfo/{id}")
    public ResultVO<?> getOrderInfo(@PathVariable String id) {
        try {
            return ResultVOUtil.success(orderService.getOrderInfo(id), "");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，查找订单详情失败");
        }
    }

    @PostMapping(value = "/updateOrder")
    public ResultVO<?> getOrderInfo(@RequestParam String id, @RequestParam boolean isMemberEditing) {
        try {
            return ResultVOUtil.success(orderService.updateOrder(id, isMemberEditing), "更新订单成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，更新订单失败");
        }
    }
}
