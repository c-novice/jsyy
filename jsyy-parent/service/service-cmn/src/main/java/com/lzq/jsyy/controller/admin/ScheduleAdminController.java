package com.lzq.jsyy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.ScheduleService;
import com.lzq.jsyy.vo.cmn.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/admin/schedule")
public class ScheduleAdminController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, ScheduleQueryVo scheduleQueryVo) {
        Page<Schedule> pageParam = new Page<>(page, limit);
        Page<Schedule> pageModel = scheduleService.selectPage(pageParam, scheduleQueryVo);

        return Result.ok(pageModel);
    }

    @PostMapping("/add")
    public Result add(Schedule schedule) {
        Map<String, Object> map = scheduleService.add(schedule);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(null, resultCodeEnum);
    }

    @PutMapping("/update")
    public Result update(Schedule schedule) {
        Map<String, Object> map = scheduleService.change(schedule);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(null, resultCodeEnum);
    }

    @DeleteMapping("/delete")
    public Result delete(Schedule schedule) {
        boolean delete = scheduleService.removeById(schedule);

        return delete ? Result.ok() : Result.fail();
    }
}
