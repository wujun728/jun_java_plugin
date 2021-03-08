package org.typroject.tyboot.component.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.typroject.tyboot.core.foundation.utils.CommonUtil;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;
import java.io.Serializable;
import java.util.Date;

@Configuration
public class ActiveMqConfig {


    public static final String DEFAULT_QUEUE        = "DEFAULT_QUEUE";
    public static final String DEFAULT_TOPIC        = "DEFAULT_TOPIC";
    public static final String DEFAULT_QUEUEREPLY   = "DEFAULT_QUEUEREPLY";
    public static final String DEFAULT_TOPIC_LISTENERCONTAINER   = "jmsTopicListenerContainerFactory";
    public static final String OUT_REPLYTO_QUEUE    = "OUT_REPLYTO_QUEUE";


    //如果要使用topic类型的消息，则需要配置该bean
    @Bean(DEFAULT_TOPIC_LISTENERCONTAINER)
    public JmsListenerContainerFactory jmsTopicListenerContainerFactory(
            ConnectionFactory connectionFactory
    ){

        DefaultJmsListenerContainerFactory factory
                = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true); //这里必须设置为true，false则表示是queue类型
        return factory;
    }


    @Bean(DEFAULT_QUEUE)
    public ActiveMQQueue queue() {
        return new ActiveMQQueue(DEFAULT_QUEUE) ;
    }

    @Bean(DEFAULT_TOPIC)
    public Topic topic() {
        return new ActiveMQTopic(DEFAULT_TOPIC) ;
    }

    @Bean(DEFAULT_QUEUEREPLY)
    public ActiveMQQueue queuereply() {
        return new ActiveMQQueue(DEFAULT_QUEUEREPLY) ;
    }


    public  static JmsMessage buildMessage(String queue, String messageHandler, Serializable body)
    {
        JmsMessage amqpMessage = new JmsMessage();
        amqpMessage.setCreateTime(new Date());
        amqpMessage.setMessageId(CommonUtil.getUUID());
        amqpMessage.setQueue(queue);
        amqpMessage.setMessageHandler(messageHandler);
        amqpMessage.setBody(body);
        return amqpMessage;
    }



}
