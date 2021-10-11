package com.lzq.jsyy.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.vo.order.PaymentInfoQueryVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface PaymentInfoService extends IService<PaymentInfo> {
    /**
     * 分页条件查询订单
     *
     * @param pageParam
     * @param paymentInfoQuery
     * @return
     */
    Page<PaymentInfo> selectPage(Page<PaymentInfo> pageParam, PaymentInfoQueryVo paymentInfoQuery);

    /**
     * 下订单
     *
     * @param paymentInfo
     * @return
     */
    Map<String, Object> order(PaymentInfo paymentInfo);

    /**
     * 取消订单
     *
     * @param id
     * @return
     */
    boolean cancel(String id);
}
