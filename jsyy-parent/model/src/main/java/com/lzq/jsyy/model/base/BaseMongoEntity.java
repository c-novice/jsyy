package com.lzq.jsyy.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * mongodb对象基类
 *
 * @author lzq
 */
@Data
@ToString
public class BaseMongoEntity implements Serializable {

    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    private Integer isDeleted;

    @ApiModelProperty(value = "其他参数")
    @Transient
    private Map<String, Object> param = new HashMap<>();
}
