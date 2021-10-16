package com.lzq.jsyy.vo.cmn.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "预约排班条件查询类")
@ToString
public class ScheduleQueryVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "房间编号")
    private String roomId;

    @ApiModelProperty("预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
}
