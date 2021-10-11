package com.lzq.jsyy.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.model.cmn.Facility;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.cmn.mapper.FacilityMapper;
import com.lzq.jsyy.cmn.service.FacilityService;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.vo.cmn.FacilityQueryVo;
import com.lzq.jsyy.vo.cmn.RoomQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class FacilityServiceImpl extends ServiceImpl<FacilityMapper, Facility> implements FacilityService {
    @Autowired
    private RoomService roomService;

    @Override
    public Page<Facility> selectPage(Page<Facility> pageParam, FacilityQueryVo facilityQueryVo) {
        if (StringUtils.isEmpty(facilityQueryVo)) {
            return null;
        }

        String facilityId = facilityQueryVo.getFacilityId();
        String name = facilityQueryVo.getName();

        QueryWrapper<Facility> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper.eq("facility_id", facilityId);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        Page<Facility> page = baseMapper.selectPage(pageParam, wrapper);
        // 补充教室
        Page<Room> pageParam2 = new Page<>(1, Integer.MAX_VALUE);
        for (int i = 0; i < page.getRecords().size(); i++) {
            Facility facility = page.getRecords().get(i);
            RoomQueryVo roomQueryVo = new RoomQueryVo();
            roomQueryVo.setFacilityId(facility.getFacilityId());
            facility.setRooms(roomService.selectPage(pageParam2, roomQueryVo).getRecords());
            facility.setRoomCount(roomService.count(facility.getFacilityId()));
        }
        return page;
    }

    @Override
    public Map<String, Object> add(Facility facility) {
        Map<String, Object> map = new HashMap<>(1);
        if (StringUtils.isEmpty(facility)) {
            map.put("state", ResultCodeEnum.FACILITY_ADD_ERROR);
            return map;
        }

        String facilityId = facility.getFacilityId();
        String name = facility.getName();
        String id = facility.getId();

        QueryWrapper<Facility> wrapper1 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper1.eq("facility_id", facilityId);
        }
        Facility facility1 = baseMapper.selectOne(wrapper1);
        if (!StringUtils.isEmpty(facility1)) {
            map.put("state", ResultCodeEnum.FACILITY_ADD_ERROR);
            return map;
        }
        wrapper1.ne("id", id);

        QueryWrapper<Facility> wrapper2 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper1.eq("name", name);
        }
        Facility facility2 = baseMapper.selectOne(wrapper2);
        if (!StringUtils.isEmpty(facility2)) {
            map.put("state", ResultCodeEnum.FACILITY_ADD_ERROR);
            return map;
        }
        wrapper2.ne("id", id);

        baseMapper.insert(facility);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Override
    public Map<String, Object> change(Facility facility) {
        Map<String, Object> map = new HashMap<>(1);
        if (StringUtils.isEmpty(facility)) {
            map.put("state", ResultCodeEnum.FACILITY_CHANGE_ERROR);
            return map;
        }

        String facilityId = facility.getFacilityId();
        String name = facility.getName();
        String id = facility.getId();

        QueryWrapper<Facility> wrapper1 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper1.eq("facility_id", facilityId);
        }
        Facility facility1 = baseMapper.selectOne(wrapper1);
        if (!StringUtils.isEmpty(facility1)) {
            map.put("state", ResultCodeEnum.FACILITY_CHANGE_ERROR);
            return map;
        }
        wrapper1.ne("id", id);

        QueryWrapper<Facility> wrapper2 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper1.eq("name", name);
        }
        wrapper2.ne("id", id);

        baseMapper.updateById(facility);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Override
    public Facility get(FacilityQueryVo facilityQueryVo) {
        if (StringUtils.isEmpty(facilityQueryVo)) {
            return null;
        }

        String facilityId = facilityQueryVo.getFacilityId();

        QueryWrapper<Facility> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper.eq("facility_id", facilityId);
        }
        Facility facility = baseMapper.selectOne(wrapper);
        if (StringUtils.isEmpty(facility)) {
            return null;
        }

        Page<Room> pageParam = new Page<>(1, Integer.MAX_VALUE);
        RoomQueryVo roomQueryVo = new RoomQueryVo();
        roomQueryVo.setFacilityId(facility.getFacilityId());

        facility.setRooms(roomService.selectPage(pageParam, roomQueryVo).getRecords());
        facility.setRoomCount(roomService.count(facility.getFacilityId()));
        return facility;
    }
}
