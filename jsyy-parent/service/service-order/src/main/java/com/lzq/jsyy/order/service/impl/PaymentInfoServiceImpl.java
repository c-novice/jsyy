package com.lzq.jsyy.order.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.common.exception.JsyyException;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.enums.OrderInfoStatusEnum;
import com.lzq.jsyy.enums.PaymentInfoStatusEnum;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.order.mapper.PaymentInfoMapper;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.order.service.WechatService;
import com.lzq.jsyy.vo.order.PaymentInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {
    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private WechatService wechatService;

    @Cacheable(value = "selectPage", keyGenerator = "keyGenerator")
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
    public Map<String, Object> pay(String orderId) throws Exception {
        Map<String, Object> map = new HashMap<>(2);
        if (StringUtils.isEmpty(orderId)) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if (ObjectUtils.isEmpty(orderInfo)) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        // 如果已经有支付中的该订单，拒绝添加
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(paymentInfo) || !paymentInfo.getPaymentStatus().equals(PaymentInfoStatusEnum.PAYING.getStatus())) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        // 获取微信支付二维码
        Map<String, Object> native2 = wechatService.createNative(orderId);
        if (ObjectUtils.isEmpty(native2)) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        // 添加支付记录，支付中
        PaymentInfo paymentInfo2 = new PaymentInfo();
        paymentInfo2.setOrderId(orderId);
        paymentInfo.setOutTradeNo(orderInfo.getOutTradeNo());
        paymentInfo.setTotalAmount(orderInfo.getAmount());
        paymentInfo.setPaymentStatus(PaymentInfoStatusEnum.PAYING.getStatus());
        baseMapper.insert(paymentInfo);
        map.put("paymentInfo", paymentInfo);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Transactional(rollbackFor = JsyyException.class)
    @Override
    public boolean cancel(String outTradeNo) {
        if (StringUtils.isEmpty(outTradeNo)) {
            return false;
        }
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", outTradeNo);
        wrapper.eq("payment_status", PaymentInfoStatusEnum.PAYING);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);

        if (ObjectUtils.isEmpty(paymentInfo)) {
            return false;
        }

        // 更新订单状态为取消
        paymentInfo.setPaymentStatus(PaymentInfoStatusEnum.CANCELED.getStatus());
        baseMapper.updateById(paymentInfo);

        // 更新预约记录为失效
        OrderInfo orderInfo = orderInfoService.getById(paymentInfo.getOrderId());
        orderInfo.setOrderStatus(OrderInfoStatusEnum.LOSE_EFFICACY.getStatus());
        orderInfoService.updateById(orderInfo);

        return true;
    }

    @Override
    public void success(String outTradeNo, Map<String, String> resultMap) {
        // 更新支付记录
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", outTradeNo);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);

        paymentInfo.setPaymentStatus(PaymentInfoStatusEnum.PAYED.getStatus());
        paymentInfo.setCallbackTime(String.valueOf(new Date()));
        paymentInfo.setTradeNo(resultMap.get("transaction_id"));
        paymentInfo.setCallbackContent(resultMap.toString());
        baseMapper.updateById(paymentInfo);

        // 更新订单记录
        OrderInfo orderInfo = orderInfoService.getById(paymentInfo.getOrderId());
        orderInfo.setOrderStatus(OrderInfoStatusEnum.ORDERED.getStatus());
        orderInfoService.updateById(orderInfo);
    }

    @Cacheable(value = "getByOutTradeNo", keyGenerator = "keyGenerator")
    @Override
    public PaymentInfo getByOutTradeNo(String outTradeNo) {
        if (StringUtils.isEmpty(outTradeNo)) {
            return null;
        }

        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();

        wrapper.eq("out_trade_no", outTradeNo);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void loss(String outTradeNo) {
        if (StringUtils.isEmpty(outTradeNo)) {
            return;
        }
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", outTradeNo);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(paymentInfo)) {
            return;
        }
        paymentInfo.setPaymentStatus(PaymentInfoStatusEnum.OVER_TIME.getStatus());
        baseMapper.updateById(paymentInfo);
    }
}
