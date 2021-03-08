package com.zh.springbootrabbitmq.config;

import com.zh.springbootrabbitmq.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @date 2019/7/9
 */
@Slf4j
@Component
public class RabbitMQProduct {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendFanoutMsg(User user){
        this.rabbitTemplate.convertAndSend("exchange_fanout",null,user);
    }

    public void sendDirectMsg(User user,String routingKey) {
        this.rabbitTemplate.convertAndSend("exchange_direct",routingKey,user);
    }

    public void sendTopicMsg(User user,String routingKey) {
        this.rabbitTemplate.convertAndSend("exchange_topic",routingKey,user);
    }

    public void sendAlternateMsg(User user, String routingKey) {
        this.rabbitTemplate.convertAndSend("exchange_alt_direct",routingKey,user);
    }

    public void sendTTLDLXDelayMsg(User user) {
        log.info("============TTLDLX延时队列发送消息=============");
        this.rabbitTemplate.convertAndSend("exchange_ttl_fanout",null,user);
    }

    public void sendDelayMsg(User user, String routingKey, long expiration) {
        log.info("============delay exchange延时队列发送消息=============");
        this.rabbitTemplate.convertAndSend(
                "exchange_delay",
                routingKey,
                user,
                message -> {
                    message.getMessageProperties().setHeader("x-delay",expiration);
                    return message;
                }
        );
    }
}
