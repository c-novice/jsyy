package com.lzq.jsyy.order.controller.api;

import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.order.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/wechat")
public class WechatApiController {
    @Autowired
    private WechatService wechatService;

    @GetMapping("/auth/queryPayStatus")
    public Result queryPayStatus(@PathVariable String orderId) {
        Map<String, String> resultMap = wechatService.queryPayStatus(orderId);
        if (ObjectUtils.isEmpty(resultMap)) {
            return Result.fail();
        }
        if ("SUCCESS".equals(resultMap.get("trade_state"))) {
            //支付成功，更新订单状态
            String out_trade_no = resultMap.get("out_trade_no");  //订单编码
            paymentService.paySuccess(out_trade_no, resultMap);
            return Result.ok().message("支付成功");
        }
        return Result.ok().message("支付中");
    }

}
