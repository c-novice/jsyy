package com.lzq.jsyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.mapper.ScheduleMapper;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.ScheduleService;
import com.lzq.jsyy.vo.cmn.ScheduleQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {
    @Override
    public Page<Schedule> selectPage(Page<Schedule> pageParam, ScheduleQueryVo scheduleQueryVo) {
        if (StringUtils.isEmpty(scheduleQueryVo)) {
            return null;
        }

        String roomId = scheduleQueryVo.getRoomId();
        Date workDate = scheduleQueryVo.getWorkDate();

        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roomId)) {
            wrapper.eq("room_id", roomId);
        }
        if (!StringUtils.isEmpty(workDate)) {
            wrapper.eq("work_date", workDate);
        }

        Page<Schedule> page = baseMapper.selectPage(pageParam, wrapper);

        return page;
    }

    @Override
    public Map<String, Object> add(Schedule schedule) {
        Map<String, Object> map = new HashMap<>(1);

        if (StringUtils.isEmpty(schedule)) {
            map.put("state", ResultCodeEnum.SCHEDULE_ADD_ERROR);
            return map;
        }

        // 判断时间段是否已被占据
        String workDate = schedule.getWorkDate();
        String beginTime = schedule.getBeginTime();
        String endTime = schedule.getEndTime();
        String id = schedule.getId();

        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(workDate)) {
            wrapper.eq("work_date", workDate);
        }
        if (!StringUtils.isEmpty(beginTime)) {
            wrapper.ge("begin_time", beginTime);
        }
        if (!StringUtils.isEmpty(endTime)) {
            wrapper.le("end_time", endTime);
        }
        wrapper.ne("id", id);

        Schedule schedule1 = baseMapper.selectOne(wrapper);

        if (StringUtils.isEmpty(schedule1)) {
            map.put("state", ResultCodeEnum.SUCCESS);
            baseMapper.insert(schedule);
        } else {
            map.put("state", ResultCodeEnum.SCHEDULE_ADD_ERROR);
        }

        return map;
    }

    @Override
    public Map<String, Object> change(Schedule schedule) {
        Map<String, Object> map = new HashMap<>(1);

        if (StringUtils.isEmpty(schedule)) {
            map.put("state", ResultCodeEnum.SCHEDULE_CHANGE_ERROR);
            return map;
        }

        // 判断时间段是否已被占据
        String workDate = schedule.getWorkDate();
        String beginTime = schedule.getBeginTime();
        String endTime = schedule.getEndTime();
        String id = schedule.getId();

        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(workDate)) {
            wrapper.eq("work_date", workDate);
        }
        if (!StringUtils.isEmpty(beginTime)) {
            wrapper.ge("begin_time", beginTime);
        }
        if (!StringUtils.isEmpty(endTime)) {
            wrapper.le("end_time", endTime);
        }
        wrapper.ne("id", id);

        Schedule schedule1 = baseMapper.selectOne(wrapper);

        if (StringUtils.isEmpty(schedule1)) {
            map.put("state", ResultCodeEnum.SUCCESS);
            baseMapper.updateById(schedule);
        } else {
            map.put("state", ResultCodeEnum.SCHEDULE_CHANGE_ERROR);
        }

        return map;
    }

    @Override
    public Schedule get(ScheduleQueryVo scheduleQueryVo) {
        if (ObjectUtils.isEmpty(scheduleQueryVo)) {
            return null;
        }

        String scheduleId = scheduleQueryVo.getScheduleId();
        QueryWrapper<Schedule> query = new QueryWrapper<>();
        query.eq("schedule_id", scheduleId);

        return baseMapper.selectOne(query);
    }
}
