package com.zh.springbootactivemq.service.impl;

import com.zh.springbootactivemq.model.User;
import com.zh.springbootactivemq.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * @author Wujun
 * @date 2019/6/11
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private Queue queueString;

    @Autowired
    private Queue queueUser;

    @Autowired
    private Queue queueString2Way;

    @Autowired
    private Topic topicString;

    @Autowired
    private Topic topicUser;

    @Autowired
    private Topic delayTopicString;

    @Autowired
    private Queue queueStringACK;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    @Override
    public void sendQueueMsg(String msg) throws JMSException {
        log.info("product发送信息:{}到{}",msg,this.queueString.getQueueName());
        this.jmsMessagingTemplate.convertAndSend(this.queueString,msg);
    }

    @Override
    public void sendQueueMsg(User user) throws JMSException {
        log.info("product发送信息:{}到{}",user.toString(),this.queueUser.getQueueName());
        this.jmsMessagingTemplate.convertAndSend(this.queueUser,user);
    }

    @Override
    public void send2WayQueueMsg(String msg) throws JMSException {
        log.info("product发送信息:{}到{}",msg,this.queueString2Way.getQueueName());
        this.jmsMessagingTemplate.convertAndSend(this.queueString2Way,msg);
    }

    @Override
    public void sendACKQueueMsg(String msg) throws JMSException {
        log.info("product发送信息:{}到{}",msg,this.queueStringACK.getQueueName());
        this.jmsMessagingTemplate.convertAndSend(this.queueStringACK,msg);
    }

    @JmsListener(destination = "queue_string_return_test",containerFactory = "queueListenerFactory")
    public void receiveQueue(String text) {
        log.info("product收到queue_string_return_test信息:{}",text);
    }

    @Override
    public void sendTopicMsg(String msg) throws JMSException {
        log.info("product发送信息:{}到{}",msg,this.topicString.getTopicName());
        this.jmsMessagingTemplate.convertAndSend(this.topicString,msg);
    }

    @Override
    public void sendTopicMsg(User user) throws JMSException {
        log.info("product发送信息:{}到{}",user.toString(),this.topicUser.getTopicName());
        this.jmsMessagingTemplate.convertAndSend(this.topicUser,user);
    }

    /**
     * 使用延时队列需要在activemq.xml中的<broker></broker>标签里添加schedulerSupport="true"，如下：
     * <broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" dataDirectory="${activemq.data}" schedulerSupport="true"></broker>
     * @return
     */
    @Override
    public void sendDelayTopicMsg(String msg) throws JMSException {
        log.info("product发送延时信息:{}到{},发送时间:{}",msg,this.delayTopicString.getTopicName(), LocalDateTime.now());
        this.jmsMessagingTemplate.getJmsTemplate().send(this.delayTopicString, session -> {
            TextMessage tx = session.createTextMessage(msg);
            tx.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,10 * 1000);
            return tx;
        });
    }

    /**
     * 使用延时队列需要在activemq.xml中的<broker></broker>标签里添加schedulerSupport="true"，如下：
     * <broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" dataDirectory="${activemq.data}" schedulerSupport="true"></broker>
     * @return
     */
    @Override
    public void sendDelayTopicMsg(String msg,long time) throws JMSException {
        log.info("product发送延时信息:{}到{},发送时间:{}",msg,this.delayTopicString.getTopicName(), LocalDateTime.now());
        this.jmsMessagingTemplate.getJmsTemplate().send(this.delayTopicString, session -> {
            TextMessage tx = session.createTextMessage(msg);
            tx.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,time);
            return tx;
        });
    }

}
