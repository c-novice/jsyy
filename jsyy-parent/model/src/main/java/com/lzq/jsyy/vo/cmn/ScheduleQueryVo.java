package com.lzq.jsyy.vo.cmn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "预约排班条件查询")
@ToString
public class ScheduleQueryVo {
    @ApiModelProperty(value = "教室编号")
    private String roomId;

    @ApiModelProperty(value = "预约编号")
    private String scheduleId;

    @ApiModelProperty("预约日期")
    private Date workDate;
}
