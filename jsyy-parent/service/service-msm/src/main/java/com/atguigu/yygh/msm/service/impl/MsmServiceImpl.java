package com.atguigu.yygh.msm.service.impl;


import com.atguigu.yygh.msm.service.MsmService;
import com.atguigu.yygh.msm.utils.ConstantPropertiesUtils;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.lzq.jsyy.vo.msm.MsmVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @author lzq
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信服务
     *
     * @param phone
     * @param sixBitRandom
     * @return
     */
    @Override
    public boolean send(String phone, String sixBitRandom) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        // 容联云发送短信
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);

        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        sdk.setAccount(ConstantPropertiesUtils.ACCOUNT_SID, ConstantPropertiesUtils.ACCOUNT_TOKEN);
        //请使用管理控制台中已创建应用的APPID
        sdk.setAppId(ConstantPropertiesUtils.APP_ID);
        sdk.setBodyType(BodyType.Type_JSON);
        String templateId = "1";
        //传入验证码
        String[] datas = {sixBitRandom, "2"};

        HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId, datas);
        return "000000".equals(result.get("statusCode"));
    }

    /**
     * mq使用的，订单发送短信服务
     *
     * @param msmVo
     * @return
     */
    @Override
    public boolean send(MsmVo msmVo) {
        if (!StringUtils.isEmpty(msmVo.getPhone())) {
            boolean isSend = this.send(msmVo.getPhone(), "11111");
            return isSend;
        }
        return false;
    }

}
