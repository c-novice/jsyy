package com.lzq.jsyy.model.cmn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 房间
 *
 * @author lzq
 */
@Data
@ApiModel(description = "教室")
public class Room extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "房间编号")
    @TableField("room_id")
    private String roomId;

    @ApiModelProperty(value = "教室类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "座位数")
    @TableField("seating")
    private Integer seating;

    @ApiModelProperty(value = "教室描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "预约规则")
    private List<Schedule> schedules;
}
