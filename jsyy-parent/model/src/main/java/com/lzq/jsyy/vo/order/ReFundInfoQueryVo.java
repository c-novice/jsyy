package com.lzq.jsyy.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lzq
 */
@ApiModel(description = "退单记录条件查询")
@Data
public class ReFundInfoQueryVo {
    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    @ApiModelProperty(value = "退款状态")
    private Integer refundStatus;

    @ApiModelProperty(value = "回调时间")
    private Date callbackTime;
}
