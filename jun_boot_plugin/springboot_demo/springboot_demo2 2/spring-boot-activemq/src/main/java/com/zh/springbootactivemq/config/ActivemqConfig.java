package com.zh.springbootactivemq.config;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * 特别注意：
 * 如果是springboot1.5+，注意更换apache的连接池依赖
 * @author Wujun
 * @date 2019/6/11
 */
@Configuration
@EnableJms
public class ActivemqConfig {

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> queueListenerACKFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        /**
         * ack设置为activemq独有的单条确认模式：4,
         * 至于为什么不设置为客户端手动确认：2,因为客户端手动确认会失效,
         * 原因为spring框架会判断是否是2，如果是2会spring框架会帮你确认,
         * 可查看AbstractMessageListenerContainer[commitIfNecessary()]
         */
        factory.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConcurrency("1");
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public Queue queueString(){
        return new ActiveMQQueue("queue_string_test");
    }

    @Bean
    public Queue queueUser(){
        return new ActiveMQQueue("queue_user_test");
    }

    @Bean
    public Queue queueString2Way(){
        return new ActiveMQQueue("queue_string_2way_test");
    }

    @Bean
    public Queue queueStringACK(){
        return new ActiveMQQueue("queue_string_ack_test");
    }

    @Bean
    public Topic topicString(){
        return new ActiveMQTopic("topic_string_test");
    }

    @Bean
    public Topic topicUser(){
        return new ActiveMQTopic("topic_user_test");
    }

    @Bean
    public Topic delayTopicString(){
        return new ActiveMQTopic("topic_delay_string_test");
    }
}
