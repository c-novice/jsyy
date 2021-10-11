package com.lzq.jsyy.order.controller.admin;

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
@RequestMapping("/admin/redund")
public class RefundInfoAdminController {
    @Autowired
    private RefundInfoService refundInfoService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RefundInfoQueryVo refundInfoQuery) {
        Page<RefundInfo> pageParam = new Page<>(page, limit);
        Page<RefundInfo> pageModel = refundInfoService.selectPage(pageParam, refundInfoQuery);

        return Result.ok(pageModel);
    }
}
