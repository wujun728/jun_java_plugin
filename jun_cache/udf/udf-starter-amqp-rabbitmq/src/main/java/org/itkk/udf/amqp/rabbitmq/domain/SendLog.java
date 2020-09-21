package org.itkk.udf.amqp.rabbitmq.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * SendLog
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SendLog {

    /**
     * 日志ID
     */
    private String logId;

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
    private Date msgCreateDate;

    /**
     * 描述 : 消息发送时间
     */
    private Date sendDate;

    /**
     * 描述 : 消息体(json字符串)
     */
    private String msgJsonString;
}
