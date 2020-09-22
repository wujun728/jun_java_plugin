package com.jun.plugin.activemq_spring.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.activemq.command.ActiveMQObjectMessage;


/** 
 * topic消息消费者C
 * @author Wujun
 *
 */
public class TopicConsumerC implements MessageListener{

    @Override
    public void onMessage(Message m) {
        System.out.println("【TopicConsumerC】接收到了消息...原生消息的信息如下(未转换之前的消息)：" + m);
        //ActiveMQTextMessage om = (ActiveMQTextMessage) m;
        
        //由于消息是一个实体bean，所以使用ActiveMQObjectMessage，如果是文本，则使用ActiveMQTextMessage
        ActiveMQObjectMessage om = (ActiveMQObjectMessage) m;
        try {
            System.out.println("实体bean消息:" + om.getObject());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("消息的其他信息：");
        System.out.println("目的地destination:" + om.getJMSDestination());
        System.out.println("type:" + om.getJMSType());
        System.out.println("messageId:" + om.getJMSMessageID());
        System.out.println("time:" + om.getJMSTimestamp());
        System.out.println("priority:" + om.getJMSPriority());
        System.out.println();
    }
}
