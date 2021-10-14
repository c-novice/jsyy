package com.lzq.jsyy.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;

/**
 * 支付记录
 *
 * @author lzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "支付记录实体类")
@TableName("payment_info")
@ToString
public class PaymentInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty(value = "对外业务编号")
    @TableField("out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "交易编号")
    @TableField("trade_no")
    private String tradeNo;

    @ApiModelProperty(value = "支付金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "支付状态")
    @TableField("payment_status")
    private Integer paymentStatus;

    @ApiModelProperty(value = "回调时间")
    @TableField("callback_time")
    private String callbackTime;

    @ApiModelProperty(value = "回调信息")
    @TableField("callback_content")
    private String callbackContent;
}
