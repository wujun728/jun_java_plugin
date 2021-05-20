package com.smq.listener;

import com.smq.entity.Email;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.*;

public class ConsumerMessageListener implements MessageListener {

    private MessageConverter messageConverter;

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            //这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
            TextMessage textMsg = (TextMessage) message;
            System.out.println("接收到一个纯文本消息。");
            try {
                System.out.println("消息内容是：" + textMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else if (message instanceof MapMessage) {
            MapMessage mapMessage = (MapMessage) message;
        } else if (message instanceof ObjectMessage) {
            ObjectMessage objMessage = (ObjectMessage) message;
            try {
				/*Object obj = objMessage.getObject();
				Email email = (Email) obj;*/
                Email email = (Email) messageConverter.fromMessage(objMessage);
                System.out.println("接收到一个ObjectMessage，包含Email对象。");
                System.out.println(email);
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

}
