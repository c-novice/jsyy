package com.lzq.jsyy.order.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.enums.OrderInfoStatusEnum;
import com.lzq.jsyy.enums.PaymentInfoStatusEnum;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.order.mapper.PaymentInfoMapper;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.common.exception.JsyyException;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.vo.order.PaymentInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {
    @Autowired
    OrderInfoService orderInfoService;

    @Override
    public Page<PaymentInfo> selectPage(Page<PaymentInfo> pageParam, PaymentInfoQueryVo paymentInfoQuery) {
        if (ObjectUtils.isEmpty(paymentInfoQuery)) {
            return null;
        }
        String orderId = paymentInfoQuery.getOrderId();
        Integer paymentStatus = paymentInfoQuery.getPaymentStatus();
        String callbackTime = paymentInfoQuery.getCallbackTime();


        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(orderId)) {
            wrapper.eq("order_id", orderId);
        }
        if (!StringUtils.isEmpty(callbackTime)) {
            wrapper.eq("call_back_time", callbackTime);
        }
        if (!StringUtils.isEmpty(paymentStatus)) {
            wrapper.eq("payment_status", paymentStatus);
        }

        Page<PaymentInfo> page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }

    @Transactional(rollbackFor = JsyyException.class)
    @Override
    public Map<String, Object> order(PaymentInfo paymentInfo) {
        Map<String, Object> map = new HashMap<>(2);
        if (ObjectUtils.isEmpty(paymentInfo)) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        // 如果已经有支付中的该订单，拒绝添加
        String orderId = paymentInfo.getOrderId();
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        PaymentInfo paymentInfo2 = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(paymentInfo2) || !paymentInfo2.getPaymentStatus().equals(PaymentInfoStatusEnum.PAYING.getStatus())) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        baseMapper.insert(paymentInfo);
        map.put("paymentInfo", paymentInfo);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Override
    public boolean cancel(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", id);
        wrapper.eq("payment_status", PaymentInfoStatusEnum.PAYING);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);

        if (ObjectUtils.isEmpty(paymentInfo)) {
            return false;
        }

        // 更新订单状态为取消
        paymentInfo.setPaymentStatus(PaymentInfoStatusEnum.CANCELED.getStatus());
        baseMapper.updateById(paymentInfo);

        // 更新预约记录为过期
        OrderInfo orderInfo = orderInfoService.getById(id);
        orderInfo.setOrderStatus(OrderInfoStatusEnum.OVER_TIME.getStatus());
        orderInfoService.updateById(orderInfo);

        return true;
    }
}
