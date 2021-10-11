package com.lzq.jsyy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.common.exception.JsyyException;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.enums.RefundInfoStatusEnum;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.model.order.RefundInfo;
import com.lzq.jsyy.order.mapper.RefundInfoMapper;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.order.service.RefundInfoService;
import com.lzq.jsyy.vo.order.RefundInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author lzq
 */
@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @Override
    public Page<RefundInfo> selectPage(Page<RefundInfo> pageParam, RefundInfoQueryVo refundInfoQuery) {
        if (ObjectUtils.isEmpty(refundInfoQuery)) {
            return null;
        }
        String orderId = refundInfoQuery.getOrderId();
        Integer refundStatus = refundInfoQuery.getRefundStatus();
        String callbackTime = refundInfoQuery.getCallbackTime();


        QueryWrapper<RefundInfo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(orderId)) {
            wrapper.eq("order_id", orderId);
        }
        if (!StringUtils.isEmpty(callbackTime)) {
            wrapper.eq("call_back_time", callbackTime);
        }
        if (!StringUtils.isEmpty(refundStatus)) {
            wrapper.eq("refund_status", refundStatus);
        }

        Page<RefundInfo> page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }

    @Transactional(rollbackFor = JsyyException.class)
    @Override
    public boolean apply(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }

        // 已经存在退款记录则退款成功
        QueryWrapper<RefundInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", id);
        wrapper.eq("refund_status", RefundInfoStatusEnum.REFUNDED);
        RefundInfo refundInfo = baseMapper.selectOne(wrapper);

        if (!ObjectUtils.isEmpty(refundInfo)) {
            return true;
        }

        // 生成退款记录
        PaymentInfo paymentInfo = paymentInfoService.getById(id);
        if (ObjectUtils.isEmpty(paymentInfo)) {
            throw new JsyyException(ResultCodeEnum.DATA_ERROR);
        }

        RefundInfo refundInfo2 = new RefundInfo();
        refundInfo2.setRefundStatus(RefundInfoStatusEnum.REFUNDING.getStatus());
        refundInfo2.setTotalAmount(paymentInfo.getTotalAmount());
        refundInfo2.setOrderId(paymentInfo.getOrderId());

        baseMapper.insert(refundInfo2);

        // TODO 微信退款

        // 根据微信退款结果更新退款状态、订单状态、预约记录状态

        return false;
    }
}
