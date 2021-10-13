package com.lzq.jsyy.cmn.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.ScheduleService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.vo.cmn.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/admin/schedule")
@Api(tags = "预约排班后台管理端API")
public class ScheduleAdminController {
    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "分页条件查询")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, ScheduleQueryVo scheduleQueryVo) {
        Page<Schedule> pageParam = new Page<>(page, limit);
        Page<Schedule> pageModel = scheduleService.selectPage(pageParam, scheduleQueryVo);

        return Result.ok(pageModel);
    }

    @ApiOperation(value = "添加预约排班")
    @PostMapping("/auth/add")
    public Result add(Schedule schedule) {
        Map<String, Object> map = scheduleService.add(schedule);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(schedule, resultCodeEnum);
    }

    @ApiOperation(value = "修改预约排班信息")
    @PutMapping("/auth/update")
    public Result update(Schedule schedule) {
        Map<String, Object> map = scheduleService.change(schedule);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(schedule, resultCodeEnum);
    }

    @ApiOperation(value = "删除预约信息")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = scheduleService.removeById(id);

        return delete ? Result.ok() : Result.fail();
    }
}
