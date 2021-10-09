package com.lzq.jsyy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.RoomService;
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
@RequestMapping("/admin/room")
public class RoomAdminController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RoomQueryVo roomQueryVo) {
        Page<Room> pageParam = new Page<>(page, limit);
        Page<Room> pageModel = roomService.selectPage(pageParam, roomQueryVo);

        return Result.ok(pageModel);
    }

    @PostMapping("/add")
    public Result add(Room room) {
        Map<String, Object> map = roomService.add(room);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(null, resultCodeEnum);
    }

    @PutMapping("/update")
    public Result update(Room room) {
        Map<String, Object> map = roomService.change(room);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(null, resultCodeEnum);
    }

    @DeleteMapping("/delete")
    public Result delete(Room room) {
        boolean delete = roomService.removeById(room);

        return delete ? Result.ok() : Result.fail();
    }

}
