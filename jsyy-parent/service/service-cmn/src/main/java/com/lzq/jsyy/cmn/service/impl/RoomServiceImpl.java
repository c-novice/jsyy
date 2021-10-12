package com.lzq.jsyy.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.cmn.mapper.RoomMapper;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.vo.cmn.RoomQueryVo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Cacheable(value = "selectPage", keyGenerator = "keyGenerator")
    @Override
    public Page<Room> selectPage(Page<Room> pageParam, RoomQueryVo roomQueryVo) {
        if (StringUtils.isEmpty(roomQueryVo)) {
            return null;
        }

        String facilityId = roomQueryVo.getFacilityId();
        String roomId = roomQueryVo.getRoomId();
        String type = roomQueryVo.getType();
        Integer seatingLow = roomQueryVo.getSeatingLow();
        Integer seatingHigh = roomQueryVo.getSeatingHigh();

        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper.eq("facility_id", facilityId);
        }
        if (!StringUtils.isEmpty(roomId)) {
            wrapper.eq("room_id", roomId);
        }
        if (!StringUtils.isEmpty(type)) {
            wrapper.eq("type", type);
        }
        if (!StringUtils.isEmpty(seatingLow)) {
            wrapper.ge("seating_low", seatingLow);
        }
        if (!StringUtils.isEmpty(seatingHigh)) {
            wrapper.le("seating_high", seatingHigh);
        }

        Page<Room> page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }


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

    @Cacheable(value = "get", keyGenerator = "keyGenerator")
    @Override
    public Room get(RoomQueryVo roomQueryVo) {
        if (StringUtils.isEmpty(roomQueryVo)) {
            return null;
        }

        String roomId = roomQueryVo.getRoomId();

        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roomId)) {
            wrapper.eq("room_id", roomId);
        }
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer count(String facilityId) {
        if (StringUtils.isEmpty(facilityId)) {
            return null;
        }

        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("facility_id", facilityId);

        return baseMapper.selectCount(wrapper);
    }
}
