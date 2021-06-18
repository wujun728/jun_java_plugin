package org.typroject.tyboot.component.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.utils.CommonUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 子杨 on 2017/4/24.
 */
@Component
public class AmqpSender {

    private static Logger logger =  LoggerFactory.getLogger(AmqpSender.class);

    @Autowired
     private   AmqpTemplate rabbitTemplate;
    public  void send(AmqpMessage amqpMessage) {

        logger.info("发送mq消息，队列："+amqpMessage.getQueue());
        rabbitTemplate.convertAndSend(amqpMessage.getQueue(), amqpMessage);
    }


    /**
     * 创建通用消息
     * @param queue  队列名称
     * @param messageHandler  消息处理器
     * @param body  消息内容
     * @return
     */
    public  static AmqpMessage buildMessage(String queue,String messageHandler,Serializable body)
    {
        AmqpMessage amqpMessage = new AmqpMessage();
        amqpMessage.setCreateTime(new Date());
        amqpMessage.setMessageId(CommonUtil.getUUID());
        amqpMessage.setQueue(queue);
        amqpMessage.setMessageHandler(messageHandler);
        amqpMessage.setBody(body);
        return amqpMessage;
    }




}
