package org.typroject.tyboot.component.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@Component
public class JMSSender {

    @Resource(name = ActiveMqConfig.DEFAULT_QUEUE)
    private Queue queue;

    @Resource(name = ActiveMqConfig.DEFAULT_TOPIC)
    private Topic topic;

    @Resource(name = ActiveMqConfig.DEFAULT_QUEUEREPLY)
    private Queue queuereply;

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage(Destination destination, final JmsMessage message){
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * 向默认队列发送消息
     * @param message
     */
    public void sendQueueMessage(final JmsMessage message){
        sendMessage(queue, message);
    }


    /**
     * 向默认主题发送消息
     * @param message
     */
    public void sendTopicMessage(final JmsMessage message){
        sendMessage(topic, message);
    }

    public void sendQueueMessageReply(final JmsMessage message){
        sendMessage(queuereply, message);
    }


    /**
     * 接收默认队列的消息反馈
     */
    //生产者监听消费者的应答
    @JmsListener(destination = ActiveMqConfig.OUT_REPLYTO_QUEUE)
    public void consumerMessage(JmsMessage message){
        System.out.println("从out.replyTo.queue收到报文"+message);
    }
}
