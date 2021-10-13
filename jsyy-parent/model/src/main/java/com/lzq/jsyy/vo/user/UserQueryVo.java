package com.lzq.jsyy.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "用户条件查询类")
@ToString
public class UserQueryVo {
    @ApiModelProperty(value = "学号")
    private String studentNumber;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户类型")
    private String type;
}
