package com.lzq.jsyy.vo.msm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 短信实体类
 *
 * @author lzq
 */
@Data
@ApiModel(description = "短信实体")
public class MsmVo implements Serializable {

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "短信模板code")
    private String templateCode;

    @ApiModelProperty(value = "短信模板参数")
    private Map<String, Object> param;
}
