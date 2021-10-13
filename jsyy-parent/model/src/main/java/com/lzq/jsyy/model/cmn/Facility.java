package com.lzq.jsyy.model.cmn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 设施
 *
 * @author lzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "设施实体类")
@TableName("facility")
@ToString
public class Facility extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设施编号")
    @TableField("facility_id")
    private String facilityId;

    @ApiModelProperty(value = "设施名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "设施描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "教室数")
    @TableField(exist = false)
    private Integer roomCount;

    @ApiModelProperty(value = "教室")
    @TableField(exist = false)
    private List<Room> rooms;

}
