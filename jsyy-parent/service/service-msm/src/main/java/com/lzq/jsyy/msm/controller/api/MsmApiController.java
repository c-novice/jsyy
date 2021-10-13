package com.lzq.jsyy.msm.controller.api;


import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.msm.service.MsmService;
import com.lzq.jsyy.msm.utils.RandomUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/msm")
@ApiModel(description = "短信操作API")
public class MsmApiController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送手机验证码
     *
     * @param phone
     * @return
     */
    @ApiOperation(value = "发送手机验证码")
    @GetMapping("/send/{phone}")
    public Result sendCode(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }

        String sixBitRandom = RandomUtil.getSixBitRandom();

        boolean isSend = msmService.send(phone, sixBitRandom);
        if (isSend) {
            redisTemplate.opsForValue().set(phone, sixBitRandom, 2, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.fail();
        }
    }
}
