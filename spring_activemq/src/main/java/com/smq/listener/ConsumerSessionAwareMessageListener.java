package com.smq.listener;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.*;

public class ConsumerSessionAwareMessageListener implements
        SessionAwareMessageListener<TextMessage> {

    private Destination destination;

    public void onMessage(TextMessage message, Session session) throws JMSException {
        System.out.println("session收到一条消息");
        System.out.println("消息内容是：" + message.getText());
        //dong
        destination = session.createQueue("queue");
        MessageProducer producer = session.createProducer(destination);
        Message textMessage = session.createTextMessage("session发送一条消息ConsumerSessionAwareMessageListener。。。");
        producer.send(textMessage);
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

}
