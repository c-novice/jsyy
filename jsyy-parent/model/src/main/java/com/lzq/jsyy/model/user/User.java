package com.lzq.jsyy.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户
 *
 * @author lzq
 */
@Data
@ApiModel(description = "用户")
@TableName("user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "学号")
    @TableField("student_number")
    private String studentNumber;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "账号")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "用户类型")
    @TableField("type")
    private String type;

    /**
     * 用户认证后分配
     */
    @ApiModelProperty(value = "权限")
    @TableField("permission")
    private String permission;

    @ApiModelProperty(value = "是否认证")
    @TableField("is_auth")
    private boolean isAuth;
}
