package com.lzq.jsyy.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.vo.order.OrderInfoQueryVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 条件分页查询
     *
     * @param pageParam
     * @param orderInfoQueryVo
     * @return
     */
    Page<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    /**
     * 添加预约订单
     *
     * @param orderInfoQueryVo
     * @return
     */
    Map<String, Object> add(OrderInfoQueryVo orderInfoQueryVo);

    /**
     * 修改预约订单
     *
     * @param orderInfo
     * @return
     */
    Map<String, Object> change(OrderInfo orderInfo);

    /**
     * 删除预约订单记录
     *
     * @param outTradeNo
     * @return
     */
    boolean delete(String outTradeNo);

    /**
     * 根据对外业务编号查询订单
     *
     * @param outTradeNo
     * @return
     */
    OrderInfo getByOutTradeNo(String outTradeNo);
}
