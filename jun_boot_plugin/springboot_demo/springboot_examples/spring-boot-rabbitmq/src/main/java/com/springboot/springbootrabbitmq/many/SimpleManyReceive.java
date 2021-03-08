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
public class SimpleManyReceive {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String message) {
        logger.info("Receive1 :{}", message);
    }
}
