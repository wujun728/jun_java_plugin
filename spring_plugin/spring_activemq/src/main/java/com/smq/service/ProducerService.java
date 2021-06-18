package com.smq.service;

import javax.jms.Destination;
import java.io.Serializable;

public interface ProducerService {

    /**
     * 发送普通的纯文本消息
     * @param destination
     * @param message
     */
    public void sendMessage(Destination destination, String message);

    /**
     * 发送一个ObjectMessage
     * @param destination
     * @param obj
     */
    public void sendMessage(Destination destination, Serializable obj);
    public void  sendWithConversion();

}
