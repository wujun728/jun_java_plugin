package com.springboot.springbootrabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FanoutSend
 */
@Component
public class FanoutSend {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String message = "Hello FanoutSend.";
        logger.info("send:{}", message);
        this.rabbitTemplate.convertAndSend("fanoutExchange","", message);
    }
}
