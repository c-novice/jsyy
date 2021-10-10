package com.lzq.jsyy.model.user;

import com.lzq.jsyy.model.base.BaseMongoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author lzq
 */
@Data
@EqualsAndHashCode
@ApiModel(description = "校园账号")
@Document("account")
public class Account extends BaseMongoEntity {
    @ApiModelProperty(value = "学号")
    @Indexed
    private String studentNumber;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户类型")
    private String type;

}