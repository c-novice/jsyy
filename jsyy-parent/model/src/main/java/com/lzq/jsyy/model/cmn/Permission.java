package com.lzq.jsyy.model.cmn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限
 *
 * @author lzq
 */
@Data
@ApiModel(description = "权限类")
@TableName("permission")
public class Permission extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "父权限")
    @TableField("father")
    private String father;
}
