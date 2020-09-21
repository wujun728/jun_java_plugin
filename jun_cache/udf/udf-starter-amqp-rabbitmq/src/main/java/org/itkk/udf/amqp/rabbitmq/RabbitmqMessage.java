/**
 * Message.java
 * Created at 2016-09-19
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.amqp.rabbitmq;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 描述 : 消息
 *
 * @author wangkang
 */
@Data
@ToString
public class RabbitmqMessage<T> implements Serializable {

    /**
     * 描述 : id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 消息ID
     */
    private String id;

    /**
     * 发送者
     */
    private String sender;

    /**
     * 描述 : 交换机
     */
    private String exchange;

    /**
     * 描述 : 路由键
     */
    private String routingKey;

    /**
     * 描述 : 时间戳
     */
    private Long timestamp;

    /**
     * 描述 : 消息创建时间
     */
    private Date createDate;

    /**
     * 描述 : 消息发送时间
     */
    private Date sendDate;

    /**
     * 描述 : 消息头
     */
    private Map<String, String> header;

    /**
     * 描述 : 消息体
     */
    private T body; //NOSONAR

    /**
     * 描述 : 构造函数
     */
    public RabbitmqMessage() {

    }

    /**
     * 描述 : 构造函数
     *
     * @param header 消息头
     * @param body   消息体
     */
    public RabbitmqMessage(Map<String, String> header, T body) {
        this.id = UUID.randomUUID().toString();
        this.createDate = new Date();
        this.header = header;
        this.body = body;
    }

}
