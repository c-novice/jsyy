package com.lzq.jsyy.order.service;

import java.util.Map;

/**
 * @author lzq
 */
public interface WechatService {
    /**
     * 生成微信二维码
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    Map<String, Object> createNative(String orderId) throws Exception;

    /**
     * 查询订单状态
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    Map<String, String> queryPayStatus(String orderId) throws Exception;

    /**
     * 微信退款
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    Map<String, String> refund(String orderId) throws Exception;
}
