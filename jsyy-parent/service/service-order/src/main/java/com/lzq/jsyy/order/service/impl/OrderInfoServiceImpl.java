package com.lzq.jsyy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.cmn.client.PermissionFeignClient;
import com.lzq.jsyy.cmn.client.ScheduleFeignClient;
import com.lzq.jsyy.common.exception.JsyyException;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.enums.OrderInfoStatusEnum;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.order.mapper.OrderInfoMapper;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.rabbit.constant.MqConst;
import com.lzq.jsyy.rabbit.service.RabbitService;
import com.lzq.jsyy.user.client.UserFeignClient;
import com.lzq.jsyy.vo.msm.MsmVo;
import com.lzq.jsyy.vo.order.add.OrderInfoAddVo;
import com.lzq.jsyy.vo.order.query.OrderInfoQueryVo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author lzq
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Autowired
    private ScheduleFeignClient scheduleFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private PermissionFeignClient permissionFeignClient;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private RabbitService rabbitService;

    @Override
    public Page<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo) {
        if (ObjectUtils.isEmpty(orderInfoQueryVo)) {
            return null;
        }
        String username = orderInfoQueryVo.getUsername();
        String facilityId = orderInfoQueryVo.getFacilityId();
        String roomId = orderInfoQueryVo.getRoomId();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();
        String workDate = orderInfoQueryVo.getWorkDate();
        String lastPendingUsername = orderInfoQueryVo.getLastPendingUsername();
        String lastPendingPermission = orderInfoQueryVo.getLastPendingPermission();

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
        if (!StringUtils.isEmpty(workDate)) {
            wrapper.eq("work_date", workDate);
        }
        if (!StringUtils.isEmpty(lastPendingUsername)) {
            wrapper.eq("last_pending_username", lastPendingUsername);
        }
        if (!StringUtils.isEmpty(lastPendingPermission)) {
            wrapper.eq("last_pending_permission", lastPendingPermission);
        }
        if (!ObjectUtils.isEmpty(orderStatus)) {
            wrapper.eq("order_status", orderStatus);
        }

        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Transactional(rollbackFor = JsyyException.class)
    @Override
    public Map<String, Object> add(OrderInfoAddVo orderInfoAddVo) {
        Map<String, Object> map = new HashMap<>(1);
        if (ObjectUtils.isEmpty(orderInfoAddVo)) {
            map.put("state", ResultCodeEnum.ORDER_ADD_ERROR);
            return map;
        }

        // ????????????????????????
        Schedule schedule = scheduleFeignClient.getById(orderInfoAddVo.getScheduleId());
        if (ObjectUtils.isEmpty(schedule)) {
            map.put("state", ResultCodeEnum.ORDER_ADD_ERROR);
            return map;
        }
        // ??????????????????????????????????????????????????????
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        if (StringUtils.isEmpty(schedule.getOpenDate()) || currentTime.compareTo(schedule.getOpenDate()) < 0) {
            map.put("state", ResultCodeEnum.ORDER_ADD_ERROR);
            return map;
        }
        if (StringUtils.isEmpty(schedule.getCloseDate()) || currentTime.compareTo(schedule.getCloseDate()) > 0) {
            map.put("state", ResultCodeEnum.ORDER_ADD_ERROR);
            return map;
        }

        // ??????????????????????????????
        if (!schedule.getIsOrdered().equals(0)) {
            map.put("state", ResultCodeEnum.ORDER_ADD_ERROR);
            return map;
        }

        // ????????????????????????????????????
        QueryWrapper<OrderInfo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("schedule_id", orderInfoAddVo.getScheduleId());
        wrapper2.ne("order_status", OrderInfoStatusEnum.LOSE_EFFICACY.getStatus());
        OrderInfo orderInfo2 = baseMapper.selectOne(wrapper2);

        // ????????????????????????????????????
        if (!ObjectUtils.isEmpty(orderInfo2)) {
            map.put("state", ResultCodeEnum.ORDER_ADD_ERROR);
            return map;
        }

        OrderInfo orderInfo = new OrderInfo();
        // ??????????????????
        orderInfo.setUsername(orderInfoAddVo.getUsername());
        orderInfo.setFacilityId(orderInfoAddVo.getFacilityId());
        orderInfo.setScheduleId(orderInfoAddVo.getScheduleId());
        orderInfo.setRoomId(orderInfoAddVo.getRoomId());
        orderInfo.setLastPendingPermission(orderInfoAddVo.getLastPendingPermission());
        // ????????????????????????
        orderInfo.setBeginTime(schedule.getBeginTime());
        orderInfo.setEndTime(schedule.getEndTime());
        orderInfo.setQuitDate(schedule.getQuitDate());
        orderInfo.setAmount(schedule.getAmount());
        orderInfo.setWorkDate(schedule.getWorkDate());
        orderInfo.setLastPendingPermission(schedule.getLastPendingPermission());
        // ?????????????????????
        orderInfo.setOutTradeNo(System.currentTimeMillis() + "" + new Random().nextInt(100));
        // ????????????????????????????????????
        orderInfo.setOrderStatus(OrderInfoStatusEnum.PAYING.getStatus());
        // ????????????????????????????????????
        orderInfo.setLastPendingUsername(orderInfo.getUsername());
        // ?????????????????????
        String permissionName = userFeignClient.getPermissionByUsername(orderInfo.getUsername());
        if (!StringUtils.isEmpty(permissionName)) {
            // ??????????????????????????????
            orderInfo.setNextNeedPermission(permissionFeignClient.getFatherByName(permissionName));
        }

        baseMapper.insert(orderInfo);
        // ??????????????????
        QueryWrapper<OrderInfo> query = new QueryWrapper<>();
        query.eq("out_trade_no", orderInfo.getOutTradeNo());
        paymentInfoService.add(baseMapper.selectOne(query));

        map.put("state", ResultCodeEnum.SUCCESS);
        map.put("orderInfo", orderInfo);

        return map;
    }

    @Override
    public boolean delete(String outTradeNo) {
        if (StringUtils.isEmpty(outTradeNo)) {
            return false;
        }
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", outTradeNo);
        OrderInfo orderInfo = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(orderInfo)) {
            return false;
        }

        // ??????????????????????????????
        if (!orderInfo.getOrderStatus().equals(OrderInfoStatusEnum.LOSE_EFFICACY.getStatus())) {
            return false;
        }

        baseMapper.deleteById(orderInfo);
        return true;
    }

    @Cacheable(value = "getByOutTradeNo", keyGenerator = "keyGenerator")
    @Override
    public OrderInfo getByOutTradeNo(String outTradeNo) {
        if (StringUtils.isEmpty(outTradeNo)) {
            return null;
        }

        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();

        wrapper.eq("out_trade_no", outTradeNo);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Page<OrderInfo> selectPendingOrder(Page<OrderInfo> pageParam, String permissionName) {
        if (StringUtils.isEmpty(permissionName)) {
            return null;
        }
        // ??????????????????????????????????????????????????????????????????????????????
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("next_need_permission", permissionName);
        wrapper.eq("order_status", OrderInfoStatusEnum.PENDING.getStatus());

        // ??????????????????????????????????????????????????????????????????????????????
        Page<OrderInfo> page = baseMapper.selectPage(pageParam, wrapper);
        for (int i = 0; i < page.getRecords().size(); ++i) {
            OrderInfo orderInfo = page.getRecords().get(i);
            String workDate = orderInfo.getWorkDate();
            if (String.valueOf(System.currentTimeMillis()).compareTo(workDate) >= 0) {
                orderInfo.setOrderStatus(OrderInfoStatusEnum.LOSE_EFFICACY.getStatus());
                baseMapper.updateById(orderInfo);
            }
        }

        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public Map<String, Object> pending(String username, String outTradeNo, Integer status) {
        Map<String, Object> map = new HashMap<>(2);
        if (StringUtils.isEmpty(outTradeNo)) {
            map.put("state", ResultCodeEnum.PENDING_ERROR);
            return map;
        }
        // ????????????
        OrderInfo orderInfo = getByOutTradeNo(outTradeNo);
        if (ObjectUtils.isEmpty(orderInfo) || !orderInfo.getOrderStatus().equals(OrderInfoStatusEnum.PENDING.getStatus())) {
            map.put("state", ResultCodeEnum.PENDING_ERROR);
            return map;
        }
        // ??????????????????
        orderInfo.setLastPendingUsername(username);
        // ????????????
        if (status.equals(OrderInfoStatusEnum.REFUSED.getStatus())) {
            orderInfo.setOrderStatus(status);
        } else {
            // ????????????????????????????????????????????????????????????
            // ??????????????????
            String permissionName = userFeignClient.getPermissionByUsername(username);
            if (orderInfo.getLastPendingPermission().equals(permissionName)) {
                orderInfo.setOrderStatus(OrderInfoStatusEnum.ORDERED.getStatus());
            } else {
                // ??????????????????????????????
                String father = permissionFeignClient.getFatherByName(permissionName);
                orderInfo.setNextNeedPermission(father);
            }
        }
        baseMapper.updateById(orderInfo);
        map.put("orderInfo", orderInfo);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Override
    public void orderTips() {
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("work_date", new DateTime().toString("yyyy-MM-dd"));
        wrapper.ne("order_status", OrderInfoStatusEnum.ORDERED);
        List<OrderInfo> orderInfoList = baseMapper.selectList(wrapper);
        // ??????????????????????????????????????????
        for (OrderInfo orderInfo : orderInfoList) {
            MsmVo msmVo = new MsmVo();
            msmVo.setPhone(orderInfo.getUsername());
            msmVo.setTemplateId("1");
            String[] params = new String[]{orderInfo.getRoomId()};
            msmVo.setParams(params);
            rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo);
        }
    }

    /**
     * ?????????????????????????????????????????????
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
