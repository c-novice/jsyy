package com.lzq.jsyy.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "用户搜索条件")
public class UserQueryVo {
    @ApiModelProperty(value = "学号")
    private String studentNumber;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户类型")
    private String type;
}
