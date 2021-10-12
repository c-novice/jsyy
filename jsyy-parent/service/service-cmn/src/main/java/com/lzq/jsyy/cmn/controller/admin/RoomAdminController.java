package com.lzq.jsyy.cmn.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.vo.cmn.RoomQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 教室
 *
 * @author lzq
 */
@RestController
@RequestMapping("/auth/admin/room")
public class RoomAdminController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RoomQueryVo roomQueryVo) {
        Page<Room> pageParam = new Page<>(page, limit);
        Page<Room> pageModel = roomService.selectPage(pageParam, roomQueryVo);

        return Result.ok(pageModel);
    }

    @PostMapping("/auth/add")
    public Result add(Room room) {
        Map<String, Object> map = roomService.add(room);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(room, resultCodeEnum);
    }

    @PutMapping("/auth/update")
    public Result update(Room room) {
        Map<String, Object> map = roomService.change(room);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(room, resultCodeEnum);
    }

    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = roomService.removeById(id);

        return delete ? Result.ok() : Result.fail();
    }

}
