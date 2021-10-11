package com.lzq.jsyy.order.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.order.RefundInfo;
import com.lzq.jsyy.order.service.RefundInfoService;
import com.lzq.jsyy.vo.order.RefundInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/redund")
public class RefundInfoApiController {
    @Autowired
    private RefundInfoService refundInfoService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RefundInfoQueryVo refundInfoQuery) {
        Page<RefundInfo> pageParam = new Page<>(page, limit);
        Page<RefundInfo> pageModel = refundInfoService.selectPage(pageParam, refundInfoQuery);

        return Result.ok(pageModel);
    }

    @DeleteMapping("/auth/apply")
    public Result apply(String id) {
        boolean apply = refundInfoService.apply(id);
        return apply ? Result.ok() : Result.fail();
    }
}
