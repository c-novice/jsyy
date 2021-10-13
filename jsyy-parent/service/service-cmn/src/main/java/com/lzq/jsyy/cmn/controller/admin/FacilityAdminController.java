package com.lzq.jsyy.cmn.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.FacilityService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Facility;
import com.lzq.jsyy.vo.cmn.FacilityQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 设施
 *
 * @author lzq
 */
@RestController
@RequestMapping("/admin/facility")
@Api(tags = "设施后台管理端API")
public class FacilityAdminController {
    @Autowired
    private FacilityService facilityService;

    @ApiOperation(value = "分页条件查询")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, FacilityQueryVo facilityQueryVo) {
        Page<Facility> pageParam = new Page<>(page, limit);
        Page<Facility> pageModel = facilityService.selectPage(pageParam, facilityQueryVo);

        return Result.ok(pageModel);
    }

    @ApiOperation(value = "添加设施")
    @PostMapping("/auth/add")
    public Result add(Facility facility) {
        Map<String, Object> map = facilityService.add(facility);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");

        return Result.build(facility, resultCodeEnum);
    }

    @ApiOperation(value = "修改设施信息")
    @PutMapping("/auth/update")
    public Result update(Facility facility) {
        Map<String, Object> map = facilityService.change(facility);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");

        return Result.build(facility, resultCodeEnum);
    }

    @ApiOperation(value = "删除设施")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = facilityService.removeById(id);

        return delete ? Result.ok() : Result.fail();
    }
}
