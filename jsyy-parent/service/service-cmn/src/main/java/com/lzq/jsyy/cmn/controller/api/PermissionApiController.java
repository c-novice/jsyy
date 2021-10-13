package com.lzq.jsyy.cmn.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzq.jsyy.cmn.service.PermissionService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.vo.cmn.PermissionQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/permission")
@Api(tags = "权限操作API")
public class PermissionApiController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "分页条件查询")
    @GetMapping("/auth/get")
    public Result get(PermissionQueryVo permissionVo) {
        Permission permission = permissionService.get(permissionVo);

        if (ObjectUtils.isEmpty(permission)) {
            return Result.fail(ResultCodeEnum.PERMISSION_GET_ERROR);
        } else {
            return Result.ok(permission);
        }
    }

    @ApiOperation(value = "根据用户类型获取默认权限")
    @GetMapping("/inner/getByType")
    public Permission getByType(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        return permissionService.getOne(wrapper);
    }
}
