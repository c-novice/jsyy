package com.lzq.jsyy.cmn.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.PermissionService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.vo.cmn.PermissionQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 权限
 *
 * @author lzq
 */
@RestController
@RequestMapping("/admin/cmn/permission")
public class PermissionAdminController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, PermissionQueryVo permissionQueryVo) {
        Page<Permission> pageParam = new Page<>(page, limit);
        Page<Permission> pageModel = permissionService.selectPage(pageParam, permissionQueryVo);

        return Result.ok(pageModel);
    }

    @PostMapping("/auth/add")
    public Result add(Permission permission) {
        Map<String, Object> map = permissionService.add(permission);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(permission, resultCodeEnum);
    }

    @PutMapping("/auth/update")
    public Result update(Permission permission) {
        boolean update = permissionService.updateById(permission);

        return update ? Result.ok(permission) : Result.fail();
    }

    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = permissionService.removeById(id);

        return delete ? Result.ok() : Result.fail();
    }


}
