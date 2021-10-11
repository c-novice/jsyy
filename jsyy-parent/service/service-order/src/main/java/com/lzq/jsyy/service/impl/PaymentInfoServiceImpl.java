package com.lzq.jsyy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.exception.jsyyException;
import com.lzq.jsyy.mapper.PaymentInfoMapper;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.service.PaymentInfoService;
import com.lzq.jsyy.vo.order.PaymentInfoQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author lzq
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    @Transactional(rollbackFor = jsyyException.class)
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

    @Override
    public Map<String, Object> add(PaymentInfo paymentInfo) {
        return null;
    }

    @Override
    public Map<String, Object> change(PaymentInfo paymentInfo) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
