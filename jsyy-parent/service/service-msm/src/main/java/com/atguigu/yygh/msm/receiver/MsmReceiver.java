package com.atguigu.yygh.msm.receiver;


import com.atguigu.common.rabbit.constant.MqConst;
import com.atguigu.yygh.msm.service.MsmService;
import com.lzq.jsyy.vo.msm.MsmVo;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsmReceiver {
    @Autowired
    private MsmService msmService;

    /**
     * 使用 mq进行监听
     *
     * @param msmVo
     */
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
                    exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
                    key = {MqConst.ROUTING_MSM_ITEM}
            ))
    public void send(MsmVo msmVo) {
        msmService.send(msmVo);
    }

}