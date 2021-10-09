package com.lzq.jsyy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.cmn.Facility;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.FacilityService;
import com.lzq.jsyy.vo.cmn.FacilityQueryVo;
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
public class FacilityAdminController {
    @Autowired
    private FacilityService facilityService;

    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, FacilityQueryVo facilityQueryVo) {
        Page<Facility> pageParam = new Page<>(page, limit);
        Page<Facility> pageModel = facilityService.selectPage(pageParam, facilityQueryVo);

        return Result.ok(pageModel);
    }

    @PostMapping("/add")
    public Result add(Facility facility) {
        Map<String, Object> map = facilityService.add(facility);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");

        return Result.build(null, resultCodeEnum);
    }

    @PutMapping("/update")
    public Result update(Facility facility) {
        Map<String, Object> map = facilityService.change(facility);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");

        return Result.build(null, resultCodeEnum);
    }

    @DeleteMapping("/delete")
    public Result delete(Facility facility) {
        boolean delete = facilityService.removeById(facility);

        return delete ? Result.ok() : Result.fail();
    }
}
