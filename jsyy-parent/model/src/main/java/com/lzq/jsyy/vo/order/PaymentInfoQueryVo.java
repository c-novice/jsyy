package com.lzq.jsyy.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@ApiModel(description = "支付记录条件查询")
@Data
@ToString
public class PaymentInfoQueryVo {
    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "支付状态")
    private Integer paymentStatus;

    @ApiModelProperty(value = "回调时间")
    private String callbackTime;

}
