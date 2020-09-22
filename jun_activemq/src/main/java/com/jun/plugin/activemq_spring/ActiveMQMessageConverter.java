package com.jun.plugin.activemq_spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * 消息转换器
 */
@Component("activeMQMessageConverter")
public class ActiveMQMessageConverter implements MessageConverter {
    
    /**
     * 将发送的实体bean对象封装为Message消息
     *  对已经实现MessageListener接口的消费者产生作用
     * 
     * @param obj
     * @param session
     * @return
     * @throws JMSException
     * (non-Javadoc)
     * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object, javax.jms.Session)
     */
    public Message toMessage(Object obj, Session session) throws JMSException {
        System.out.println();
        System.out.println("将实体bean对象转换为Message消息: " + obj);
        
        //发送的消息对象类型是PersonInfo
        if (obj instanceof PersonInfo) {
            ActiveMQObjectMessage msg = (ActiveMQObjectMessage) session.createObjectMessage();
            msg.setObject((PersonInfo) obj);
            return msg;
        } else {//这里可以指定其他的消息类型
            System.out.println("Object:[" + obj + "] is not a instance of PersonInfo.");
            throw new JMSException("Object:[" + obj + "] is not a instance of PersonInfo.");
        }
    }
    
    /**
     * 将消息对象转换为对应的实体bean并返回
     *  只对未实现MessageListener接口的消息消费者产生作用，其他的已经实现MessageListener接口的，不会执行该方法
     * 
     * @param message
     * @return
     * @throws JMSException
     * (non-Javadoc)
     * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(javax.jms.Message)
     */
    public Object fromMessage(Message message) throws JMSException {
        System.out.println();
        System.out.println("执行ActiveMQMessageConverter消息转换器 ,message信息如下: " + message.toString());
        if (message instanceof ObjectMessage) {
            ObjectMessage oMsg = (ObjectMessage) message;
            if (oMsg instanceof ActiveMQObjectMessage) {
                ActiveMQObjectMessage aMsg = (ActiveMQObjectMessage) oMsg;
                try {
                    PersonInfo personInfo = (PersonInfo) aMsg.getObject();
                    personInfo.setUserName(personInfo.getUserName() + "，这是二次加工后的用户名.");
                    //这里可以对实体bean的属性进行其他处理
                    System.out.println("对实体bean进行二次加工后的结果:" + personInfo.toString());
                    
                    //对消息二次加工之后，返回最终的消息
                    return personInfo;
                } catch (Exception e) {
                    System.out.println("Message:[" + message + "] is not a instance of personInfo.");
                    throw new JMSException("Message:[" + message + "] is not a instance of personInfo.");
                }
            } else {
                System.out.println("Message:[" + message + "] is not " + "a instance of ActiveMQObjectMessage[personInfo].");
                throw new JMSException("Message:[" + message + "] is not " + "a instance of ActiveMQObjectMessage[personInfo].");
            }
        } else {
            System.out.println("Message:[" + message + "] is not a instance of ObjectMessage.");
            throw new JMSException("Message:[" + message + "] is not a instance of ObjectMessage.");
        }
    }

    
}
