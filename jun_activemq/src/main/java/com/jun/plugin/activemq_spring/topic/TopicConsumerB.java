package com.jun.plugin.activemq_spring.topic;

import javax.jms.JMSException;

import com.jun.plugin.activemq_spring.PersonInfo;

/** 
 * topic消息消费者B  
 * @author Wujun
 *
 */
public class TopicConsumerB {
    public void receiveB(PersonInfo personInfo) throws JMSException {
        System.out.println("【TopicConsumerB】收到TopicProducer的消息—->personInfo的用户名是:" + personInfo.getUserName());
        System.out.println();
    }
}
