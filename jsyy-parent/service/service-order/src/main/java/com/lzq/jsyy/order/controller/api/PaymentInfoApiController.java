package com.lzq.jsyy.order.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.order.service.WechatService;
import com.lzq.jsyy.vo.order.PaymentInfoQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/payment")
@ApiModel(description = "支付操作API")
public class PaymentInfoApiController {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiModelProperty(value = "分页条件查询支付记录")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, PaymentInfoQueryVo paymentInfoQuery) {
        Page<PaymentInfo> pageParam = new Page<>(page, limit);
        Page<PaymentInfo> pageModel = paymentInfoService.selectPage(pageParam, paymentInfoQuery);

        return Result.ok(pageModel);
    }

    @ApiModelProperty(value = "支付订单")
    @PostMapping("/auth/pay")
    public Result pay(String orderId) throws Exception {
        Map<String, Object> map = paymentInfoService.pay(orderId);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(map.get("paymentInfo"), resultCodeEnum);
    }

    @ApiModelProperty(value = "取消支付")
    @PutMapping("/auth/cancel")
    public Result cancel(String outTradeNo) {
        boolean cancel = paymentInfoService.cancel(outTradeNo);
        return cancel ? Result.ok() : Result.fail();
    }

    @ApiModelProperty(value = "支付状态查询")
    @GetMapping("/auth/queryPayStatus")
    public Result queryPayStatus(String outTradeNo) throws Exception {
        Map<String, String> resultMap = wechatService.queryPayStatus(outTradeNo);
        if (ObjectUtils.isEmpty(resultMap)) {
            return Result.fail();
        }
        if ("SUCCESS".equals(resultMap.get("trade_state"))) {
            // 支付成功，更新支付记录
            paymentInfoService.success(outTradeNo, resultMap);
            return Result.ok();
        }
        // 支付失败，若此时redis已过期，则更新订单状态
        Map payMap = (Map) redisTemplate.opsForValue().get(outTradeNo);
        if (!ObjectUtils.isEmpty(payMap)) {
            paymentInfoService.loss(outTradeNo);
        }
        return Result.fail(ResultCodeEnum.PAYING);
    }

}
