package com.lzq.jsyy.cmn.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.PermissionService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.vo.cmn.PermissionQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 权限
 *
 * @author lzq
 */
@RestController
@RequestMapping("/admin/permission")
@Api(tags = "权限后台管理端API")
public class PermissionAdminController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "分页条件查询")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, PermissionQueryVo permissionQueryVo) {
        Page<Permission> pageParam = new Page<>(page, limit);
        Page<Permission> pageModel = permissionService.selectPage(pageParam, permissionQueryVo);

        return Result.ok(pageModel);
    }

    @ApiOperation(value = "添加权限")
    @PostMapping("/auth/add")
    public Result add(Permission permission) {
        Map<String, Object> map = permissionService.add(permission);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(permission, resultCodeEnum);
    }

    @ApiOperation(value = "修改权限信息")
    @PutMapping("/auth/update")
    public Result update(Permission permission) {
        boolean update = permissionService.updateById(permission);

        return update ? Result.ok(permission) : Result.fail();
    }

    @ApiOperation(value = "删除权限")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = permissionService.removeById(id);

        return delete ? Result.ok() : Result.fail();
    }


}
