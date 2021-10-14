package com.lzq.jsyy.order.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.vo.order.OrderInfoQueryVo;
import io.swagger.annotations.ApiModel;
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
@ApiModel(description = "预约订单操作API")
public class OrderInfoApiController {
    @Autowired
    private OrderInfoService orderInfoService;

    @ApiModelProperty(value = "分页条件查询预约订单")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, OrderInfoQueryVo orderInfoQuery) {
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        Page<OrderInfo> pageModel = orderInfoService.selectPage(pageParam, orderInfoQuery);

        return Result.ok(pageModel);
    }

    @ApiModelProperty(value = "添加订单")
    @PostMapping("/auth/order")
    public Result order(OrderInfoQueryVo orderInfoQuery) {
        Map<String, Object> map = orderInfoService.add(orderInfoQuery);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(map.get("orderInfo"), resultCodeEnum);
    }

    @ApiModelProperty(value = "修改订单")
    @PutMapping("/auth/update")
    public Result update(OrderInfo orderInfo) {
        Map<String, Object> map = orderInfoService.change(orderInfo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(orderInfo, resultCodeEnum);
    }

    @ApiModelProperty(value = "删除订单记录")
    @DeleteMapping("/auth/delete")
    public Result delete(String outTradeNo) {
        boolean delete = orderInfoService.delete(outTradeNo);
        return delete ? Result.ok() : Result.fail();
    }

    // TODO 审批订单
}
