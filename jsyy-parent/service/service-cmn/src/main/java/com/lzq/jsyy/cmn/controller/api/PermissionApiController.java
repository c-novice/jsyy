package com.lzq.jsyy.cmn.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzq.jsyy.cmn.service.PermissionService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.vo.cmn.query.PermissionQueryVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/permission")
@Api(tags = "权限操作API")
public class PermissionApiController {
    @Autowired
    private PermissionService permissionService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{permission}")
    })

    @ApiIgnore()
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
