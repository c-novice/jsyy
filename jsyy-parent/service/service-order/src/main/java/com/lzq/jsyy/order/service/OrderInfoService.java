package com.lzq.jsyy.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.vo.order.OrderInfoQueryVO;

import java.util.Map;

/**
 * @author lzq
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 条件分页查询
     *
     * @param pageParam
     * @param orderInfoQuery
     * @return
     */
    Page<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderInfoQueryVO orderInfoQuery);

    /**
     * 添加预约订单
     *
     * @param orderInfo
     * @return
     */
    Map<String, Object> add(OrderInfo orderInfo);

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
     * @param id
     * @return
     */
    boolean delete(String id);
}
