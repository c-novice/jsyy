package com.lzq.jsyy.vo.cmn.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "修改教室vo类")
@ToString
public class RoomUpdateVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "设施编号")
    private String facilityId;

    @ApiModelProperty(value = "房间编号")
    private String roomId;

    @ApiModelProperty(value = "教室类型")
    private String type;

    @ApiModelProperty(value = "座位数")
    private Integer seating;

    @ApiModelProperty(value = "教室描述")
    private String description;
}
