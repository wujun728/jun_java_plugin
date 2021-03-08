package com.springboot.springbootrabbitmq.many;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SimpleManySend
 */
@Component
public class SimpleManySend {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(int i) {
        String message = "Hello Spring Boot: " + i;
        amqpTemplate.convertAndSend("simpleOneToMany", message);
        logger.info("SimpleManySend1 send {} is success.", i);
    }
}
