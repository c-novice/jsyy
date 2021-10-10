package com.lzq.jsyy.vo.cmn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "权限查询条件")
public class PermissionQueryVo {
    @ApiModelProperty(value = "用户类型")
    private String type;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "父权限名称")
    private String father;
}
