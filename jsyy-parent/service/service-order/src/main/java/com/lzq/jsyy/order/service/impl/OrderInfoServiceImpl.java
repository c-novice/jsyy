package com.lzq.jsyy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.enums.OrderInfoStatusEnum;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.order.mapper.OrderInfoMapper;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.cmn.client.ScheduleFeignClient;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.vo.order.OrderInfoQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Autowired
    private ScheduleFeignClient scheduleFeignClient;

    @Override
    public Page<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderInfoQueryVO orderInfoQuery) {
        if (ObjectUtils.isEmpty(orderInfoQuery)) {
            return null;
        }
        String username = orderInfoQuery.getUsername();
        String facilityId = orderInfoQuery.getFacilityId();
        String roomId = orderInfoQuery.getRoomId();
        String scheduleId = orderInfoQuery.getScheduleId();
        String workDate = orderInfoQuery.getWorkDate();

        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            wrapper.eq("username", username);
        }
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper.eq("facility_id", facilityId);
        }
        if (!StringUtils.isEmpty(roomId)) {
            wrapper.eq("room_id", roomId);
        }
        if (!StringUtils.isEmpty(scheduleId)) {
            wrapper.eq("schedule_id", scheduleId);
        }
        if (!StringUtils.isEmpty(workDate)) {
            wrapper.eq("work_date", workDate);
        }

        // 每次查询时检查是否过期
        Page<OrderInfo> page = baseMapper.selectPage(pageParam, wrapper);
        for (int i = 0; i < page.getRecords().size(); ++i) {
            Integer orderStatus = page.getRecords().get(i).getOrderStatus();
            String beginTime = page.getRecords().get(i).getBeginTime();
            String endTime = page.getRecords().get(i).getEndTime();
            if (updateOverTimeStatus(orderStatus, beginTime, endTime)) {
                page.getRecords().get(i).setOrderStatus(OrderInfoStatusEnum.LOSE_EFFICACY.getStatus());
            }
        }
        return page;
    }

    @Override
    public Map<String, Object> add(OrderInfo orderInfo) {
        Map<String, Object> map = new HashMap<>(1);
        if (ObjectUtils.isEmpty(orderInfo)) {
            map.put("state", ResultCodeEnum.ORDER_ADD_ERROR);
            return map;
        }

        // 查找当前预约排班
        Schedule schedule = scheduleFeignClient.getById(orderInfo.getScheduleId());
        if (ObjectUtils.isEmpty(schedule)) {
            map.put("state", ResultCodeEnum.ORDER_ADD_ERROR);
            return map;
        }

        // 查找该预约排班的预约记录
        QueryWrapper<OrderInfo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("schedule_id", orderInfo.getScheduleId());
        wrapper2.ne("order_status", OrderInfoStatusEnum.LOSE_EFFICACY.getStatus());
        Integer orderInfo2 = baseMapper.selectCount(wrapper2);

        if (orderInfo2 != 0) {
            map.put("state", ResultCodeEnum.ORDER_ADD_ERROR);
            return map;
        }

        // 支付中
        orderInfo.setOrderStatus(OrderInfoStatusEnum.PAYING.getStatus());
        baseMapper.insert(orderInfo);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Override
    public Map<String, Object> change(OrderInfo orderInfo) {
        Map<String, Object> map = new HashMap<>(1);
        if (ObjectUtils.isEmpty(orderInfo)) {
            map.put("state", ResultCodeEnum.ORDER_CHANGE_ERROR);
            return map;
        }

        // 查找当前预约排班
        Schedule schedule = scheduleFeignClient.getById(orderInfo.getScheduleId());
        if (ObjectUtils.isEmpty(schedule)) {
            map.put("state", ResultCodeEnum.ORDER_CHANGE_ERROR);
            return map;
        }


        // 查找该预约排班的预约记录
        QueryWrapper<OrderInfo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("schedule_id", orderInfo.getScheduleId());
        wrapper2.eq("username", orderInfo.getScheduleId());
        wrapper2.ne("order_status", OrderInfoStatusEnum.LOSE_EFFICACY.getStatus());
        OrderInfo orderInfo2 = baseMapper.selectOne(wrapper2);

        // 只能修改支付中的订单
        if (ObjectUtils.isEmpty(orderInfo2) || !orderInfo2.getOrderStatus().equals(OrderInfoStatusEnum.PAYING.getStatus())) {
            map.put("state", ResultCodeEnum.ORDER_CHANGE_ERROR);
            return map;
        }

        baseMapper.updateById(orderInfo);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Override
    public boolean delete(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        OrderInfo orderInfo = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(orderInfo)) {
            return false;
        }

        // 只能删除过期预约记录
        if (!orderInfo.getOrderStatus().equals(OrderInfoStatusEnum.LOSE_EFFICACY.getStatus())) {
            return false;
        }

        baseMapper.deleteById(id);
        return true;
    }

    /**
     * 每次查询预约记录时更新过期状态
     *
     * @param orderStatus
     * @param beginTime
     * @param endTime
     * @return
     */
    private boolean updateOverTimeStatus(Integer orderStatus, String beginTime, String endTime) {
        if (!orderStatus.equals(OrderInfoStatusEnum.LOSE_EFFICACY.getStatus())) {
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis());
            boolean isOverTime = false;
            if (!StringUtils.isEmpty(beginTime)) {
                isOverTime = currentTime.compareTo(beginTime) < 0;
            }
            if (!StringUtils.isEmpty(endTime) && !isOverTime) {
                isOverTime = currentTime.compareTo(endTime) > 0;
            }
            return isOverTime;
        } else {
            return true;
        }

    }
}
