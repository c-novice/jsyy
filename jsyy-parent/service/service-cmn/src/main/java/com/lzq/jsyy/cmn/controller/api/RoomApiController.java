package com.lzq.jsyy.cmn.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.cmn.service.ScheduleService;
import com.lzq.jsyy.vo.cmn.RoomQueryVo;
import com.lzq.jsyy.vo.cmn.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/room")
public class RoomApiController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RoomQueryVo roomQueryVo) {
        Page<Room> pageParam = new Page<>(page, limit);
        Page<Room> pageModel = roomService.selectPage(pageParam, roomQueryVo);

        return Result.ok(pageModel);
    }

    @GetMapping("/auth/get")
    public Result get(RoomQueryVo roomQueryVo) {
        Room room = roomService.get(roomQueryVo);

        if (ObjectUtils.isEmpty(room)) {
            return Result.fail(ResultCodeEnum.ROOM_GET_ERROR);
        } else {
            Page<Schedule> pageParam = new Page<>(1, Integer.MAX_VALUE);
            ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
            scheduleQueryVo.setRoomId(scheduleQueryVo.getRoomId());

            room.setSchedules(scheduleService.selectPage(pageParam, scheduleQueryVo).getRecords());
            return Result.ok(room);
        }
    }

}