package com.springboot.springbootrabbitmq.simple;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SimpleSend
 */
@Component
public class SimpleSend {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = "Hello Spring Boot " + simpleDateFormat.format(new Date());
        amqpTemplate.convertAndSend("simple", message);
        logger.info("消息推送成功！");
    }
}