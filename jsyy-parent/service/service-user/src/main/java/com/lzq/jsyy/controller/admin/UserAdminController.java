package com.lzq.jsyy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.UserService;
import com.lzq.jsyy.vo.user.LoginVo;
import com.lzq.jsyy.vo.user.UserQueryVo;
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
public class UserAdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Result login(LoginVo loginVo) {
        Map<String, Object> map = userService.loginByPassword(loginVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");

        return Result.build(map, resultCodeEnum);
    }

    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserQueryVo userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        Page<User> pageModel = userService.selectPage(pageParam, userQueryVo);

        return Result.ok(pageModel);
    }

    @PostMapping("/auth/add")
    public Result add(User user) {
        Map<String, Object> map = userService.add(user);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(user, resultCodeEnum);
    }

    @PutMapping("/auth/update")
    public Result update(User user) {
        boolean update = userService.updateById(user);

        return update ? Result.ok() : Result.build(user, ResultCodeEnum.USER_REPEAT);
    }

    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = userService.removeById(id);

        return delete ? Result.ok() : Result.fail( ResultCodeEnum.DELETE_FAIL);
    }

}
