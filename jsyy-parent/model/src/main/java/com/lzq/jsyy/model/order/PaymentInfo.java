package com.lzq.jsyy.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付记录
 *
 * @author lzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "支付记录")
@TableName("payment_info")
public class PaymentInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty(value = "支付金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "支付状态")
    @TableField("payment_status")
    private Integer paymentStatus;

    @ApiModelProperty(value = "回调时间")
    @TableField("callback_time")
    private Date callbackTime;


    @ApiModelProperty(value = "回调信息")
    @TableField("callback_content")
    private String callbackContent;
}
