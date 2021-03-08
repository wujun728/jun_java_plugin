package com.springboot.springbootrabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TopicSend
 */
@Component
public class TopicSend {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send1() {
        String message = "message 1";
        logger.info("send:{}", message);
        rabbitTemplate.convertAndSend("topicExchange", "topic.message", message);
    }

    public void send2() {
        String message = "message 2";
        logger.info("send:{}", message);
        rabbitTemplate.convertAndSend("topicExchange", "topic.messages", message);
    }
}
