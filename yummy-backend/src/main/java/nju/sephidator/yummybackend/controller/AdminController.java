package nju.sephidator.yummybackend.controller;


import nju.sephidator.yummybackend.service.AdminService;
import nju.sephidator.yummybackend.utils.ResultVOUtil;
import nju.sephidator.yummybackend.vo.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/getRestaurantInfoCheckList")
    public ResultVO<?> getRestaurantInfoCheckList() {
        try {
            return ResultVOUtil.success(adminService.getRestaurantInfoCheckList(), "");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，查询饭店审核信息失败");
        }
    }

    @PostMapping(value = "/approveRestaurantInfoCheck/{restaurantId}")
    public ResultVO<?> approve(@PathVariable String restaurantId) {
        try {
            adminService.approveRestaurantInfoCheck(restaurantId);
            return ResultVOUtil.success(adminService.getRestaurantInfoCheckList(), "饭店审核信息审批成功");
        } catch (Exception e) {
            return ResultVOUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，批准饭店审核信息失败");
        }
    }
}
