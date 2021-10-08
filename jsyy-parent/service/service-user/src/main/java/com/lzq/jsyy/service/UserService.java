package com.lzq.jsyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.vo.user.LoginVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface UserService extends IService<User> {
    /**
     * 根据账号、密码登录
     *
     * @param loginVo
     * @return
     */
    Map<String, Object> loginByPassword(LoginVo loginVo);

    /**
     * 根据手机号、验证码登录
     *
     * @param loginVo
     * @return
     */
    Map<String, Object> loginByCode(LoginVo loginVo);
}
