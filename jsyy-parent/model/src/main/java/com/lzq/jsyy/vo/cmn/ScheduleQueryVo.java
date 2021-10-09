package com.lzq.jsyy.vo.cmn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "预约排班条件查询")
public class ScheduleQueryVo {
    @ApiModelProperty(value = "教室编号")
    private String roomId;

    @ApiModelProperty("预约日期")
    private Date workDate;
}
