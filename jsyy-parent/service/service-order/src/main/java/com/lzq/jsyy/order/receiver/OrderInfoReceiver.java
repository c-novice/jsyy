package com.lzq.jsyy.order.receiver;


import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.rabbit.constant.MqConst;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author lzq
 */
@Service
public class OrderInfoReceiver {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 使用rabbit进行监听
     *
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_TASK_7, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_TASK),
            key = {MqConst.ROUTING_TASK_7}
    ))
    public void patientTips(Message message, Channel channel) throws IOException {
        orderInfoService.patientTips();
    }

}