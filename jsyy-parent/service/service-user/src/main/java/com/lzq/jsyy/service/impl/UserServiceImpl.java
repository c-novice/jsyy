package com.lzq.jsyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.mapper.UserMapper;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.UserService;
import com.lzq.jsyy.vo.user.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lzq
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Map<String, Object> loginByPassword(LoginVo loginVo) {
        Map<String, Object> map = new HashMap<>(1);

        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        User user = baseMapper.selectOne(wrapper);

        // 未查到用户，若已经失败5次及以上则返回LOGIN_ERROR_MULTI，否则返回LOGIN_USER_ERROR，若查到用户则返回SUCCESS
        if (null == user) {
            String oldCount = redisTemplate.opsForValue().get(username);
            int newCount = 0;
            if (null != oldCount) {
                newCount = Integer.parseInt(oldCount) + 1;
            }
            if (newCount > 5) {
                map.put("state", ResultCodeEnum.LOGIN_ERROR_MULTI);
            } else {
                map.put("state", ResultCodeEnum.LOGIN_USER_ERROR);
            }
            redisTemplate.opsForValue().set(username, String.valueOf(newCount), 3600, TimeUnit.SECONDS);
        } else {
            map.put("state", ResultCodeEnum.SUCCESS);
            redisTemplate.opsForValue().set(username, String.valueOf(0), 3600, TimeUnit.SECONDS);
        }

        return map;
    }

    @Override
    public Map<String, Object> loginByCode(LoginVo loginVo) {
        // 验证码登录不设置冻结
        Map<String, Object> map = new HashMap<>(1);

        String username = loginVo.getUsername();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);

        // 用户为空返回LOGIN_PHONE_ERROR，否则返回SUCCESS
        if (null == user) {
            map.put("state", ResultCodeEnum.LOGIN_PHONE_ERROR);
        } else {
            String code = loginVo.getCode();
            String redisCode = redisTemplate.opsForValue().get(username);
            if (redisCode == null || !redisCode.equals(code)) {
                map.put("state", ResultCodeEnum.CODE_ERROR);
            } else {
                map.put("state", ResultCodeEnum.SUCCESS);
            }
        }

        return map;
    }

}