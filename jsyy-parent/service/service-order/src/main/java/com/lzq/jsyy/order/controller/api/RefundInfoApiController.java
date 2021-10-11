package com.lzq.jsyy.order.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.order.RefundInfo;
import com.lzq.jsyy.order.service.RefundInfoService;
import com.lzq.jsyy.vo.order.RefundInfoQueryVo;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "分页条件查询退单记录")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RefundInfoQueryVo refundInfoQuery) {
        Page<RefundInfo> pageParam = new Page<>(page, limit);
        Page<RefundInfo> pageModel = refundInfoService.selectPage(pageParam, refundInfoQuery);

        return Result.ok(pageModel);
    }

    @ApiModelProperty(value = "退单申请")
    @GetMapping("/auth/apply")
    public Result apply(String id) throws Exception {
        boolean apply = refundInfoService.apply(id);
        return apply ? Result.ok() : Result.fail();
    }
}
