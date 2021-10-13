package com.lzq.jsyy.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@ApiModel(description = "退单记录条件查询类")
@Data
@ToString
public class RefundInfoQueryVo {
    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "退款状态")
    private Integer refundStatus;

    @ApiModelProperty(value = "回调时间")
    private String callbackTime;
}
