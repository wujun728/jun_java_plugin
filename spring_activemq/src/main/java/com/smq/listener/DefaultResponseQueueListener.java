package com.smq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class DefaultResponseQueueListener implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("DefaultResponseQueueListener接收到发送到defaultResponseQueue的一个文本消息，内容是：" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
