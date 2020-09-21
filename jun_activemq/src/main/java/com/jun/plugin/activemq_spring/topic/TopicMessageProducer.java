package com.jun.plugin.activemq_spring.topic;

import javax.annotation.Resource;
import javax.jms.Topic;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.jun.plugin.activemq_spring.PersonInfo;

/**
 * PUB-SUB消息生产者
 * 
 * 作者: zhoubang 
 * 日期：2015年9月28日 下午2:45:10
 */
@Component
public class TopicMessageProducer {

    /**
     * spring消息发送模版
     */
    @Resource(name="topicJmsTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 消息目的地
     */
    @Resource(name = "topicDestination")
    private Topic defaultDestination;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Topic getDefaultDestination() {
        return defaultDestination;
    }

    public void setDefaultDestination(Topic defaultDestination) {
        this.defaultDestination = defaultDestination;
    }

    public void sendTopicMessage(PersonInfo personInfo) {
        // getJmsTemplate().convertAndSend(personInfo);//如果配置文件中指定了目的地，可以使用这句话发送消息。

        System.out.println("发布/订阅 TopicMessageProducer 消息生产者开始发送消息...");
        
        //目的地、模版，都是通过注入方式引入，并不是通过配置bean的方式引入.
        getJmsTemplate().convertAndSend(this.defaultDestination, personInfo);
    }

}
