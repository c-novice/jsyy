package com.lzq.jsyy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.service.PaymentInfoService;
import com.lzq.jsyy.vo.order.PaymentInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/admin/payment")
public class PaymentInfoAdminController {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, PaymentInfoQueryVo paymentInfoQuery) {
        Page<PaymentInfo> pageParam = new Page<>(page, limit);
        Page<PaymentInfo> pageModel = paymentInfoService.selectPage(pageParam, paymentInfoQuery);

        return Result.ok(pageModel);
    }

    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = paymentInfoService.delete(id);
        return delete ? Result.ok() : Result.fail();
    }
}
