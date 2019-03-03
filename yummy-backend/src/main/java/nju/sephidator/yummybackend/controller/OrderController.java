package nju.sephidator.yummybackend.controller;

import nju.sephidator.yummybackend.exceptions.FoodInsufficientException;
import nju.sephidator.yummybackend.exceptions.MemberAmountException;
import nju.sephidator.yummybackend.service.OrderService;
import nju.sephidator.yummybackend.service.UpdateService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.order.OrderDetailVO;
import nju.sephidator.yummybackend.vo.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UpdateService updateService;

    @PostMapping(value = "/submit")
    public ResultVO<?> submit(@RequestParam String restaurantId,
                              @RequestParam String memberEmail,
                              @RequestParam Double totalAmount,
                              @RequestParam Double discount,
                              @RequestBody List<OrderDetailVO> orderDetails) {
        try {
            discount = new BigDecimal(discount).setScale(2, RoundingMode.HALF_UP).doubleValue();
            String message = orderService.submit(restaurantId, memberEmail, totalAmount, discount, orderDetails);
            return ResultVOUtil.success("", message);
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，提交订单失败");
        }
    }

    @GetMapping(value = "/getMemberOrders")
    public ResultVO<?> getMemberOrders(@RequestParam String email,
                              @RequestParam Integer orderStatus) {
        try {
            updateService.updateMemberOrders(email);
            return ResultVOUtil.success(orderService.findMemberOrders(email, orderStatus), "");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，查找订单失败");
        }
    }

    @GetMapping(value = "/getRestaurantOrders")
    public ResultVO<?> getRestaurantOrders(@RequestParam String restaurantId,
                                       @RequestParam Integer orderStatus) {
        try {
            updateService.updateRestaurantOrders(restaurantId);
            return ResultVOUtil.success(orderService.findRestaurantOrders(restaurantId, orderStatus), "");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，查找订单失败");
        }
    }

    @GetMapping(value = "/getOrderInfo/{id}")
    public ResultVO<?> getOrderInfo(@PathVariable String id) {
        try {
            updateService.updateOrder(id);
            return ResultVOUtil.success(orderService.getOrderInfo(id), "");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，查找订单详情失败");
        }
    }

    @PostMapping(value = "/payOrder/{id}")
    public ResultVO<?> payOrder(@PathVariable String id) {
        try {
            return ResultVOUtil.success(orderService.payOrder(id), "订单支付成功");
        } catch (MemberAmountException e) {
            return ResultVOUtil.error(HttpStatus.FORBIDDEN.value(), "余额不足，订单支付失败");
        } catch (FoodInsufficientException e) {
            return ResultVOUtil.error(HttpStatus.FORBIDDEN.value(), "菜品数量不足，订单提交失败");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，订单支付失败");
        }
    }

    @PostMapping(value = "/handleOrder/{id}")
    public ResultVO<?> handleOrder(@PathVariable String id) {
        try {
            return ResultVOUtil.success(orderService.handleOrder(id), "订单开始处理");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，更新订单失败");
        }
    }

    @PostMapping(value = "/finishOrder/{id}")
    public ResultVO<?> finishOrder(@PathVariable String id) {
        try {
            return ResultVOUtil.success(orderService.finishOrder(id), "订单完结成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，订单完结失败");
        }
    }

    @PostMapping(value = "/cancelOrder/{id}")
    public ResultVO<?> cancelOrder(@PathVariable String id) {
        try {
            return ResultVOUtil.success(orderService.cancelOrder(id), "取消订单成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，取消订单失败");
        }
    }
}
