package com.zh.springbootactivemq.service;

import com.zh.springbootactivemq.model.User;

import javax.jms.JMSException;

/**
 * @author Wujun
 * @date 2019/6/11
 */
public interface ProductService {

    void sendQueueMsg(String msg) throws JMSException;

    void sendQueueMsg(User user) throws JMSException;

    void send2WayQueueMsg(String msg) throws JMSException;

    void sendACKQueueMsg(String msg) throws JMSException;

    void sendTopicMsg(String msg) throws JMSException;

    void sendTopicMsg(User user) throws JMSException;

    void sendDelayTopicMsg(String msg) throws JMSException;

    void sendDelayTopicMsg(String msg,long time) throws JMSException;
}
