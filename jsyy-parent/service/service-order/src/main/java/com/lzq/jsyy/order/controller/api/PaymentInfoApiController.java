package com.lzq.jsyy.order.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.vo.order.PaymentInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lzq
 */
public class PaymentInfoApiController {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, PaymentInfoQueryVo paymentInfoQuery) {
        Page<PaymentInfo> pageParam = new Page<>(page, limit);
        Page<PaymentInfo> pageModel = paymentInfoService.selectPage(pageParam, paymentInfoQuery);

        return Result.ok(pageModel);
    }

    @PostMapping("/auth/add")
    public Result add(PaymentInfo paymentInfo) {
        Map<String, Object> map = paymentInfoService.add(paymentInfo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(paymentInfo, resultCodeEnum);
    }

    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = paymentInfoService.delete(id);
        return delete ? Result.ok() : Result.fail();
    }
}
