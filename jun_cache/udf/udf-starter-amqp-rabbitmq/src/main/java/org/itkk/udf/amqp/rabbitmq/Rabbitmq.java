/**
 * Rabbitmq.java
 * Created at 2016-11-17
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.amqp.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 描述 : Rabbitmq
 *
 * @author wangkang
 */
@Component
public class Rabbitmq {

    /**
     * 描述 : 应用名称
     */
    @Value("${spring.application.name}")
    private String springApplicationName;

    /**
     * 描述 : amqpTemplate
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * messageLog
     */
    @Autowired
    private MessageLog messageLog;

    /**
     * 描述 : Convert a Java object to an Amqp Message and send it to a specific exchange with a
     * specific routing key.
     *
     * @param exchange   exchange
     * @param routingKey routingKey
     * @param message    a message to send
     * @param <T>        任意类型
     */
    public <T> void convertAndSend(String exchange, String routingKey, RabbitmqMessage<T> message) {
        message.setSender(springApplicationName);
        message.setSendDate(new Date());
        message.setExchange(exchange);
        message.setRoutingKey(routingKey);
        message.setTimestamp(System.currentTimeMillis());
        this.amqpTemplate.convertAndSend(exchange, routingKey, message);
        messageLog.send(message);
    }

}
