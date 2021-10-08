package com.lzq.jsyy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.UserService;
import com.lzq.jsyy.vo.user.UserQueryVo;
import com.lzq.jsyy.vo.user.LoginVo;
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

        return Result.build(null, resultCodeEnum);
    }

    @GetMapping("/{page},{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserQueryVo userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        Page<User> pageModel = userService.selectPage(pageParam, userQueryVo);

        return Result.ok(pageModel);
    }

    @PostMapping("/add")
    public Result add(User user) {
        boolean save = userService.save(user);

        return save ? Result.ok() : Result.build(null, ResultCodeEnum.USER_REPEAT);
    }

    @PutMapping("/update")
    public Result update(User user) {
        boolean update = userService.updateById(user);

        return update ? Result.ok() : Result.build(null, ResultCodeEnum.USER_REPEAT);
    }

    @DeleteMapping("/delete")
    public Result delete(User user) {
        boolean delete = userService.removeById(user);

        return delete ? Result.ok() : Result.build(null, ResultCodeEnum.DELETE_FAIL);
    }

}
