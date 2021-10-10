package com.lzq.jsyy.controller.api;

import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.impl.UserServiceImpl;
import com.lzq.jsyy.vo.user.BindingVo;
import com.lzq.jsyy.vo.user.LoginVo;
import com.lzq.jsyy.vo.user.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;

/**
 * app端 用户
 *
 * @author lzq
 */
public class UserApiController {
    @Autowired
    private UserServiceImpl userService;

    /**
     * 账号、密码登录
     *
     * @param loginVo
     * @return
     */
    @GetMapping("/loginByPassword")
    public Result loginByPassword(LoginVo loginVo) {
        Map<String, Object> map = userService.loginByPassword(loginVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");

        return Result.build(map, resultCodeEnum);
    }

    /**
     * 手机号、验证码登录
     *
     * @param loginVo
     * @return
     */
    @GetMapping("/loginByCode")
    public Result loginByCode(LoginVo loginVo) {
        Map<String, Object> map = userService.loginByCode(loginVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");

        return Result.build(map, resultCodeEnum);
    }

    @GetMapping("/register")
    public Result register(RegisterVo registerVo) {
        Map<String, Object> map = userService.register(registerVo);

        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(map, resultCodeEnum);
    }

    @GetMapping("/auth/binding")
    public Result binding(String userId, BindingVo bindingVo) {
        User user = userService.getUser(userId);
        Map<String, Object> map = userService.binding(user, bindingVo);

        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        return Result.build(map, resultCodeEnum);
    }

    @PutMapping("/auth/update")
    public Result update(User user) {
        boolean update = userService.updateById(user);

        return update ? Result.ok() : Result.build(user, ResultCodeEnum.USER_REPEAT);
    }

}
