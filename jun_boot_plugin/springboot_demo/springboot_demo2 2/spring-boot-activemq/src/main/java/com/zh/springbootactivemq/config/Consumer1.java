package com.zh.springbootactivemq.config;

import com.zh.springbootactivemq.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.time.LocalDateTime;

/**
 * @author Wujun
 * @date 2019/6/11
 */
@Slf4j
@Component
public class Consumer1 {

    @JmsListener(destination = "queue_string_test",containerFactory = "queueListenerFactory")
    public void receiveQueue(String text) {
        log.info("consumer1收到queue_string信息:{}",text);
    }

    @JmsListener(destination = "queue_user_test",containerFactory = "queueListenerFactory")
    public void receiveQueue(User user) {
        log.info("consumer1收到queue_user信息:{}",user.toString());
    }

    @JmsListener(destination = "queue_string_2way_test",containerFactory = "queueListenerFactory")
    @SendTo("queue_string_return_test")
    public String receive2WayQueue(String text) {
        log.info("consumer1收到queue_string_2way信息:{}",text);
        return "queue_string_2way_test已收到消息:" + text + ",请进行下一步操作";
    }

    @JmsListener(destination = "queue_string_ack_test",containerFactory = "queueListenerACKFactory")
    public void receiveQueue(TextMessage message, Session session) throws JMSException {
        log.info("consumer2收到queue_string信息:{}",message.getText());
        try {
            int a = 1 / 0;
            //业务处理结束，手动ack通知队列收到消息，可以把消息从队列移除
            message.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage());
            /**
             * 通知队列重发，默认每秒重发1次，一共重发6次，
             * 若想自定义重试次数，重试间隔时间可以设置
             * ActiveMQConnectionFactory的RedeliveryPolicyMap的RedeliveryPolicy
             */
            session.recover();
        }
    }

    @JmsListener(destination = "topic_string_test",containerFactory = "topicListenerFactory")
    public void receiveTopic(String text) {
        log.info("consumer1收到topic_string信息:{}",text);
    }

    @JmsListener(destination = "topic_user_test",containerFactory = "topicListenerFactory")
    public void receiveTopic(User user) {
        log.info("consumer1收到topic_user信息:{}",user.toString());
    }

    @JmsListener(destination = "topic_delay_string_test",containerFactory = "topicListenerFactory")
    public void receiveDelayTopic(String text) {
        log.info("consumer1收到topic_delay_string延时信息:{},接收时间:{}",text, LocalDateTime.now());
    }
}
