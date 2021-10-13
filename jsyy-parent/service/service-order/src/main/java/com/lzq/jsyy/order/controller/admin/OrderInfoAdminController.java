package com.lzq.jsyy.order.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.vo.order.OrderInfoQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/admin/order")
@ApiModel(description="预约订单后台管理端API")
public class OrderInfoAdminController {
    @Autowired
    private OrderInfoService orderInfoService;

    @ApiModelProperty(value = "分页条件查询预约订单")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, OrderInfoQueryVo orderInfoQuery) {
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        Page<OrderInfo> pageModel = orderInfoService.selectPage(pageParam, orderInfoQuery);

        return Result.ok(pageModel);
    }
}
