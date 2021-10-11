package com.lzq.jsyy.order.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.order.service.WechatService;
import com.lzq.jsyy.vo.order.PaymentInfoQueryVo;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lzq
 */
public class PaymentInfoApiController {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private WechatService wechatService;

    @ApiModelProperty(value = "分页条件查询支付记录")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, PaymentInfoQueryVo paymentInfoQuery) {
        Page<PaymentInfo> pageParam = new Page<>(page, limit);
        Page<PaymentInfo> pageModel = paymentInfoService.selectPage(pageParam, paymentInfoQuery);

        return Result.ok(pageModel);
    }

    @ApiModelProperty(value = "支付订单")
    @PostMapping("/auth/order")
    public Result order(PaymentInfo paymentInfo) throws Exception {
        Map<String, Object> map = paymentInfoService.order(paymentInfo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(paymentInfo, resultCodeEnum);
    }

    @ApiModelProperty(value = "取消支付")
    @PutMapping("/auth/cancel")
    public Result cancel(String id) {
        boolean cancel = paymentInfoService.cancel(id);
        return cancel ? Result.ok() : Result.fail();
    }

    @GetMapping("/auth/queryPayStatus")
    public Result queryPayStatus(@PathVariable String orderId) throws Exception {
        Map<String, String> resultMap = wechatService.queryPayStatus(orderId);
        if (ObjectUtils.isEmpty(resultMap)) {
            return Result.fail(ResultCodeEnum.PAY_ERROR);
        }
        if ("SUCCESS".equals(resultMap.get("trade_state"))) {
            String outTradeNo = resultMap.get("out_trade_no");
            // 更新支付状态、更新订单状态
            paymentInfoService.paySuccess(outTradeNo, resultMap);
            return Result.ok(ResultCodeEnum.PAY_SUCCESS);
        }
        return Result.ok(ResultCodeEnum.PAYING);
    }

}
