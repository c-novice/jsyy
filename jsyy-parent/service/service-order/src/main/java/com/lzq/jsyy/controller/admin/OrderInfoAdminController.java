package com.lzq.jsyy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.service.OrderInfoService;
import com.lzq.jsyy.vo.order.OrderInfoQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/admin/order")
public class OrderInfoAdminController {
    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, OrderInfoQueryVO orderInfoQuery) {
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        Page<OrderInfo> pageModel = orderInfoService.selectPage(pageParam, orderInfoQuery);

        return Result.ok(pageModel);
    }

    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = orderInfoService.delete(id);
        return delete ? Result.ok() : Result.fail();
    }
    // TODO 数据导出

}
