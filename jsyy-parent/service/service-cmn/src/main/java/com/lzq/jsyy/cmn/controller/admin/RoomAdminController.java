package com.lzq.jsyy.cmn.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.vo.cmn.RoomQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "教室后台管理端API")
public class RoomAdminController {
    @Autowired
    private RoomService roomService;

    @ApiOperation(value = "分页条件查询")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RoomQueryVo roomQueryVo) {
        Page<Room> pageParam = new Page<>(page, limit);
        Page<Room> pageModel = roomService.selectPage(pageParam, roomQueryVo);

        return Result.ok(pageModel);
    }

    @ApiOperation(value = "添加教室")
    @PostMapping("/auth/add")
    public Result add(Room room) {
        Map<String, Object> map = roomService.add(room);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(room, resultCodeEnum);
    }

    @ApiOperation(value = "修改教室信息")
    @PutMapping("/auth/update")
    public Result update(Room room) {
        Map<String, Object> map = roomService.change(room);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(room, resultCodeEnum);
    }

    @ApiOperation(value = "删除教室")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = roomService.removeById(id);

        return delete ? Result.ok() : Result.fail();
    }

}
