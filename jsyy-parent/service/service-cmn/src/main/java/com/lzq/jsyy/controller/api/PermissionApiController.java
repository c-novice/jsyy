package com.lzq.jsyy.controller.api;

import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.PermissionService;
import com.lzq.jsyy.vo.cmn.PermissionQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionApiController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/auth/get")
    public Result get(PermissionQueryVo permissionVo) {
        Permission permission = permissionService.get(permissionVo);

        if (ObjectUtils.isEmpty(permission)) {
            return Result.fail(ResultCodeEnum.PERMISSION_GET_ERROR);
        } else {
            return Result.ok(permission);
        }
    }

    @GetMapping("/inner/get")
    public Permission getPermissionVo(PermissionQueryVo permissionVo) {
        return permissionService.get(permissionVo);
    }
}
