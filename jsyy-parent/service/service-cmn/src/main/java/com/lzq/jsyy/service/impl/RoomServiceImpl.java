package com.lzq.jsyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.mapper.RoomMapper;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.RoomService;
import com.lzq.jsyy.vo.cmn.RoomQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Override
    public Map<String, Object> add(Room room) {
        Map<String, Object> map = new HashMap<>(1);
        if (StringUtils.isEmpty(room)) {
            map.put("state", ResultCodeEnum.ROOM_ADD_ERROR);
            return map;
        }

        String roomId = room.getRoomId();
        String id = room.getId();

        QueryWrapper<Room> wrapper1 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(room)) {
            wrapper1.eq("room_id", roomId);
        }
        wrapper1.ne("id", id);

        Room room1 = baseMapper.selectOne(wrapper1);
        if (!StringUtils.isEmpty(room1)) {
            map.put("state", ResultCodeEnum.ROOM_ADD_ERROR);
            return map;
        }

        baseMapper.insert(room);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Override
    public Page<Room> selectPage(Page<Room> pageParam, RoomQueryVo roomQueryVo) {
        return null;
    }

    @Override
    public Map<String, Object> change(Room room) {
        Map<String, Object> map = new HashMap<>(1);
        if (StringUtils.isEmpty(room)) {
            map.put("state", ResultCodeEnum.ROOM_ADD_ERROR);
            return map;
        }

        String roomId = room.getRoomId();
        String id = room.getId();

        QueryWrapper<Room> wrapper1 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(room)) {
            wrapper1.eq("room_id", roomId);
        }
        wrapper1.ne("id", id);

        Room room1 = baseMapper.selectOne(wrapper1);
        if (!StringUtils.isEmpty(room1)) {
            map.put("state", ResultCodeEnum.ROOM_ADD_ERROR);
            return map;
        }

        baseMapper.updateById(room);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }
}
