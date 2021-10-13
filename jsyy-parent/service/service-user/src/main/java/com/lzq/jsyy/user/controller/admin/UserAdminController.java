package com.lzq.jsyy.user.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.user.service.UserService;
import com.lzq.jsyy.vo.user.LoginVo;
import com.lzq.jsyy.vo.user.UserQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * admin端 用户
 *
 * @author lzq
 */
@RestController
@RequestMapping("/admin/user")
@ApiModel(description = "用户后台管理端API")
public class UserAdminController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录")
    @GetMapping("/login")
    public Result login(LoginVo loginVo) {
        Map<String, Object> map = userService.loginByPassword(loginVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");

        return Result.build(map, resultCodeEnum);
    }

    @ApiOperation(value = "分页条件查询用户")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserQueryVo userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        Page<User> pageModel = userService.selectPage(pageParam, userQueryVo);

        return Result.ok(pageModel);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/auth/add")
    public Result add(User user) {
        Map<String, Object> map = userService.add(user);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(user, resultCodeEnum);
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/auth/update")
    public Result update(User user) {
        boolean update = userService.updateById(user);

        return update ? Result.ok() : Result.build(user, ResultCodeEnum.USER_REPEAT);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = userService.removeById(id);

        return delete ? Result.ok() : Result.fail(ResultCodeEnum.DELETE_FAIL);
    }

}
