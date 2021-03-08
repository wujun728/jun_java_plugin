package com.springboot.springbootrabbitmq.many;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * SimpleManyReceive
 */
@Component
@RabbitListener(queues = "simpleOneToMany")
public class SimpleManyReceive1 {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String message) {
        logger.info("Receive2 :{}", message);
    }
}
