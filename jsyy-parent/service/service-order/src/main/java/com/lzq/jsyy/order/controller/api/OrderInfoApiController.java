package com.lzq.jsyy.order.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.vo.order.OrderInfoQueryVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 预约订单信息
 *
 * @author lzq
 */
@RestController
@RequestMapping("/api/order")
public class OrderInfoApiController {
    @Autowired
    private OrderInfoService orderInfoService;

    @ApiModelProperty(value = "分页条件查询预约订单")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, OrderInfoQueryVO orderInfoQuery) {
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        Page<OrderInfo> pageModel = orderInfoService.selectPage(pageParam, orderInfoQuery);

        return Result.ok(pageModel);
    }

    @ApiModelProperty(value = "添加订单")
    @PostMapping("/auth/add")
    public Result add(OrderInfo orderInfo) {
        Map<String, Object> map = orderInfoService.add(orderInfo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(orderInfo, resultCodeEnum);
    }

    @ApiModelProperty(value = "修改订单")
    @PutMapping("/auth/update")
    public Result update(OrderInfo orderInfo) {
        Map<String, Object> map = orderInfoService.change(orderInfo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(orderInfo, resultCodeEnum);
    }

    @ApiModelProperty(value = "删除订单")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = orderInfoService.delete(id);
        return delete ? Result.ok() : Result.fail();
    }
}
