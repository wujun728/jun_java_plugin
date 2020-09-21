package org.itkk.udf.amqp.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.itkk.udf.amqp.rabbitmq.domain.CompleteLog;
import org.itkk.udf.amqp.rabbitmq.domain.ErrorLog;
import org.itkk.udf.amqp.rabbitmq.domain.ReceiveLog;
import org.itkk.udf.amqp.rabbitmq.domain.SendLog;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * MessageLog
 */
@Component
@Slf4j
public class MessageLog {

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
     * objectMapper
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * send
     *
     * @param <T>     泛型
     * @param message message
     */
    @Async
    protected <T> void send(RabbitmqMessage<T> message) {
        //将消息转换为json格式
        String msgJsonString = null;
        try {
            msgJsonString = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.warn("MessageLog.msgJsonString convert msg to string error : ", e);
        }
        //构造实体
        SendLog log = new SendLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setId(message.getId());
        log.setSender(message.getSender());
        log.setExchange(message.getExchange());
        log.setRoutingKey(message.getRoutingKey());
        log.setTimestamp(message.getTimestamp());
        log.setMsgCreateDate(message.getCreateDate());
        log.setSendDate(message.getSendDate());
        log.setMsgJsonString(msgJsonString);
        //构造消息
        RabbitmqMessage<SendLog> logMessage = new RabbitmqMessage<>();
        logMessage.setSender(springApplicationName);
        logMessage.setSendDate(new Date());
        logMessage.setExchange(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName());
        logMessage.setRoutingKey(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.SEND.name());
        logMessage.setTimestamp(System.currentTimeMillis());
        logMessage.setBody(log);
        //发送消息
        this.amqpTemplate.convertAndSend(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), RabbitmqConstant.EXCHANGE_MESSAGE_LOG.SEND.name(), logMessage);
    }

    /**
     * receive
     *
     * @param msgId       msgId
     * @param queues      queues
     * @param receiveDate receiveDate
     */
    @Async
    protected void receive(String msgId, String queues, Date receiveDate) {
        //构造实体
        ReceiveLog log = new ReceiveLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setId(msgId);
        log.setQueues(queues);
        log.setReceiveDate(receiveDate);
        //构造消息
        RabbitmqMessage<ReceiveLog> logMessage = new RabbitmqMessage<>();
        logMessage.setSender(springApplicationName);
        logMessage.setSendDate(new Date());
        logMessage.setExchange(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName());
        logMessage.setRoutingKey(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.RECEIVE.name());
        logMessage.setTimestamp(System.currentTimeMillis());
        logMessage.setBody(log);
        //发送消息
        this.amqpTemplate.convertAndSend(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), RabbitmqConstant.EXCHANGE_MESSAGE_LOG.RECEIVE.name(), logMessage);
    }

    /**
     * complete
     *
     * @param msgId        msgId
     * @param queues       queues
     * @param completeDate completeDate
     */
    @Async
    protected void complete(String msgId, String queues, Date completeDate) {
        //构造实体
        CompleteLog log = new CompleteLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setId(msgId);
        log.setQueues(queues);
        log.setCompleteDate(completeDate);
        //构造消息
        RabbitmqMessage<CompleteLog> logMessage = new RabbitmqMessage<>();
        logMessage.setSender(springApplicationName);
        logMessage.setSendDate(new Date());
        logMessage.setExchange(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName());
        logMessage.setRoutingKey(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.COMPLETE.name());
        logMessage.setTimestamp(System.currentTimeMillis());
        logMessage.setBody(log);
        //发送消息
        this.amqpTemplate.convertAndSend(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), RabbitmqConstant.EXCHANGE_MESSAGE_LOG.COMPLETE.name(), logMessage);
    }

    /**
     * error
     *
     * @param msgId     msgId
     * @param queues    queues
     * @param errorDate errorDate
     * @param ex        ex
     */
    @Async
    protected void error(String msgId, String queues, Date errorDate, Exception ex) {
        //构造实体
        ErrorLog log = new ErrorLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setId(msgId);
        log.setQueues(queues);
        log.setErrorDate(errorDate);
        log.setErrorMsg(ExceptionUtils.getStackTrace(ex));
        //构造消息
        RabbitmqMessage<ErrorLog> logMessage = new RabbitmqMessage<>();
        logMessage.setSender(springApplicationName);
        logMessage.setSendDate(new Date());
        logMessage.setExchange(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName());
        logMessage.setRoutingKey(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.ERROR.name());
        logMessage.setTimestamp(System.currentTimeMillis());
        logMessage.setBody(log);
        //发送消息
        this.amqpTemplate.convertAndSend(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), RabbitmqConstant.EXCHANGE_MESSAGE_LOG.ERROR.name(), logMessage);
    }
}
