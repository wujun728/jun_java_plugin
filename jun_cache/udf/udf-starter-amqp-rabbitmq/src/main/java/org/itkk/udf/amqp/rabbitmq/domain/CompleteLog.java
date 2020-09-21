package org.itkk.udf.amqp.rabbitmq.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * CompleteLog
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class CompleteLog {

    /**
     * 描述 : 日志ID
     */
    private String logId;

    /**
     * 描述 : 消息ID
     */
    private String id;

    /**
     * 描述 : 队列
     */
    private String queues;

    /**
     * 描述 : 完成时间
     */
    private Date completeDate;
}
