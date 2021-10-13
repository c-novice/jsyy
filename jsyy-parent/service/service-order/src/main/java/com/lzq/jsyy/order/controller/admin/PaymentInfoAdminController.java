package com.lzq.jsyy.order.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.vo.order.PaymentInfoQueryVo;
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
@RequestMapping("/admin/payment")
@ApiModel(description = "支付记录后台管理端API")
public class PaymentInfoAdminController {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @ApiModelProperty(value = "分页条件查询支付记录")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, PaymentInfoQueryVo paymentInfoQuery) {
        Page<PaymentInfo> pageParam = new Page<>(page, limit);
        Page<PaymentInfo> pageModel = paymentInfoService.selectPage(pageParam, paymentInfoQuery);

        return Result.ok(pageModel);
    }
}
