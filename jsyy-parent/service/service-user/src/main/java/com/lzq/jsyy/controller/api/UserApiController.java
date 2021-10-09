package com.lzq.jsyy.controller.api;

import com.lzq.jsyy.result.Result;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.impl.UserServiceImpl;
import com.lzq.jsyy.vo.user.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

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

        return Result.build(null, resultCodeEnum);
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

        return Result.build(null, resultCodeEnum);
    }
}
