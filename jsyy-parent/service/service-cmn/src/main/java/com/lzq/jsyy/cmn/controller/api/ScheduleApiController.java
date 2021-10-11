package com.lzq.jsyy.cmn.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.cmn.service.ScheduleService;
import com.lzq.jsyy.vo.cmn.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/schedule")
public class ScheduleApiController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, ScheduleQueryVo scheduleQueryVo) {
        Page<Schedule> pageParam = new Page<>(page, limit);
        Page<Schedule> pageModel = scheduleService.selectPage(pageParam, scheduleQueryVo);

        return Result.ok(pageModel);
    }

    @GetMapping("/auth/get")
    public Result get(ScheduleQueryVo scheduleQueryVo) {
        Schedule schedule = scheduleService.get(scheduleQueryVo);

        if (ObjectUtils.isEmpty(schedule)) {
            return Result.fail(ResultCodeEnum.SCHEDULE_GET_ERROR);
        } else {
            return Result.ok(schedule);
        }
    }

    @GetMapping("/inner/gerById")
    public Schedule getById(String id) {
        if (!StringUtils.isEmpty(id)) {
            return null;
        }
        return scheduleService.getById(id);
    }
}