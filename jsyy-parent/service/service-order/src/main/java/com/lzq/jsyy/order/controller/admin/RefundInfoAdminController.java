package com.lzq.jsyy.order.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.order.RefundInfo;
import com.lzq.jsyy.order.service.RefundInfoService;
import com.lzq.jsyy.vo.order.RefundInfoQueryVo;
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
@RequestMapping("/admin/redund")
@ApiModel(description = "退单记录后台管理端API")
public class RefundInfoAdminController {
    @Autowired
    private RefundInfoService refundInfoService;

    @ApiModelProperty(value = "分页条件查询退单记录")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RefundInfoQueryVo refundInfoQuery) {
        Page<RefundInfo> pageParam = new Page<>(page, limit);
        Page<RefundInfo> pageModel = refundInfoService.selectPage(pageParam, refundInfoQuery);

        return Result.ok(pageModel);
    }
}
