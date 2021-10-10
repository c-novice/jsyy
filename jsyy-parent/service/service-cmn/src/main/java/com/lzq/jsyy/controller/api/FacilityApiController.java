package com.lzq.jsyy.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.cmn.Facility;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.FacilityService;
import com.lzq.jsyy.vo.cmn.FacilityQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/facility")
public class FacilityApiController {
    @Autowired
    private FacilityService facilityService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, FacilityQueryVo facilityQueryVo) {
        Page<Facility> pageParam = new Page<>(page, limit);
        Page<Facility> pageModel = facilityService.selectPage(pageParam, facilityQueryVo);

        return Result.ok(pageModel);
    }

    @GetMapping("/auth/get")
    public Result get(FacilityQueryVo facilityQueryVo) {
        Facility facility = facilityService.get(facilityQueryVo);

        if (ObjectUtils.isEmpty(facility)) {
            return Result.fail(ResultCodeEnum.FACILITY_GET_ERROR);
        } else {
            return Result.ok(facility);
        }
    }
}
