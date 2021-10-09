package com.lzq.jsyy.vo.cmn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "教室条件查询")
public class RoomQueryVo {
    @ApiModelProperty(value = "房间编号")
    private String roomId;

    @ApiModelProperty(value = "房间类型")
    private String type;

    @ApiModelProperty(value = "座位数下限")
    private Integer seatingLow;

    @ApiModelProperty(value = "座位数上限")
    private Integer seatingHigh;
}
