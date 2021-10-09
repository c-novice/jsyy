package com.lzq.jsyy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.vo.cmn.RoomQueryVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface RoomService extends IService<Room> {
    /**
     * 添加一个教室
     *
     * @param room
     * @return
     */
    Map<String, Object> add(Room room);

    /**
     * 条件分页查询教室
     *
     * @param pageParam
     * @param roomQueryVo
     * @return
     */
    Page<Room> selectPage(Page<Room> pageParam, RoomQueryVo roomQueryVo);

    /**
     * 修改一个教室
     *
     * @param room
     * @return
     */
    Map<String, Object> change(Room room);
}
