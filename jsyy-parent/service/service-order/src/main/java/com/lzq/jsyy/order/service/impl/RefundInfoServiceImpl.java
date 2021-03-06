package com.lzq.jsyy.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.common.exception.JsyyException;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.enums.OrderInfoStatusEnum;
import com.lzq.jsyy.enums.RefundInfoStatusEnum;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.model.order.RefundInfo;
import com.lzq.jsyy.order.mapper.RefundInfoMapper;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.order.service.RefundInfoService;
import com.lzq.jsyy.order.service.WechatService;
import com.lzq.jsyy.vo.order.query.RefundInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private OrderInfoService orderInfoService;

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
    public boolean apply(String outTradeNo) throws Exception {
        if (StringUtils.isEmpty(outTradeNo)) {
            return false;
        }

        // ???????????????????????????????????????
        QueryWrapper<RefundInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", outTradeNo);
        wrapper.eq("refund_status", RefundInfoStatusEnum.REFUNDED);
        RefundInfo refundInfo = baseMapper.selectOne(wrapper);

        if (!ObjectUtils.isEmpty(refundInfo)) {
            return true;
        }

        // ??????????????????
        PaymentInfo paymentInfo = paymentInfoService.getByOutTradeNo(outTradeNo);
        if (ObjectUtils.isEmpty(paymentInfo)) {
            throw new JsyyException(ResultCodeEnum.DATA_ERROR);
        }

        RefundInfo refundInfo2 = new RefundInfo();
        refundInfo2.setTotalAmount(paymentInfo.getTotalAmount());
        refundInfo2.setOrderId(paymentInfo.getOrderId());
        refundInfo2.setOutTradeNo(paymentInfo.getOutTradeNo());

        // ?????????
        refundInfo2.setRefundStatus(RefundInfoStatusEnum.REFUNDING.getStatus());

        baseMapper.insert(refundInfo2);

        // ????????????
        Map<String, String> resultMap = wechatService.refund(paymentInfo);
        if (ObjectUtils.isEmpty(resultMap)) {
            // ???????????????
            return false;
        }

        // ?????????????????????????????????????????????????????????
        refundInfo2.setCallbackTime(String.valueOf(new Date()));
        refundInfo2.setTradeNo(resultMap.get("refund_id"));
        refundInfo2.setCallbackContent(JSONObject.toJSONString(resultMap));

        // ?????????
        refundInfo2.setRefundStatus(RefundInfoStatusEnum.REFUNDED.getStatus());
        baseMapper.updateById(refundInfo2);

        OrderInfo orderInfo = orderInfoService.getById(refundInfo2.getOrderId());
        if (ObjectUtils.isEmpty(orderInfo)) {
            throw new JsyyException(ResultCodeEnum.DATA_ERROR);
        }

        orderInfo.setOrderStatus(OrderInfoStatusEnum.LOSE_EFFICACY.getStatus());
        orderInfoService.updateById(orderInfo);

        return true;
    }
}
