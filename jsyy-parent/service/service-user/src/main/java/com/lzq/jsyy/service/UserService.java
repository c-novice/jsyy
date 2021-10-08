package com.lzq.jsyy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.vo.user.LoginVo;
import com.lzq.jsyy.vo.user.UserQueryVo;

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

    /**
     * 条件分页查询用户
     *
     * @param pageParam
     * @param userQueryVo
     * @return
     */
    Page<User> selectPage(Page<User> pageParam, UserQueryVo userQueryVo);
}
