package com.zh.springbootrabbitmq.config;

import com.rabbitmq.client.Channel;
import com.zh.springbootrabbitmq.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * basicNack(deliveryTag,multiple,requeue)
 * multiple:是否批量操作，true是，false否，true的时候deliveryTag以下的消息都会重回或者丢弃
 * requeue:是否回队，true是，false是丢弃，true的时候回到队列可能被其他消费者消费
 * basicReject(deliveryTag,requeue):也可以丢弃消息或者重回队列，区别是他不支持批量操作
 * basicRecover(requeue):是重发消息的意思，参数设置为true意味着消息回到队列里可能会被分配给其他消费者，设置为false意味着还分配给该消费者
 * 因为出现问题多半是处理业务出现异常，重发没意义，自己补偿即可
 * nack和reject丢弃消息时，如果交换器绑定了死信队列，还可以触发死信队列
 * @author Wujun
 * @date 2019/7/9
 */
@Slf4j
@Component
public class RabbitMQConsumer {

    /**
     * fanout
     */
    @RabbitListener(queues="queue_fanout_1")
    public void receiveQueue1SendFanoutMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_fanout_1收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_fanout_1:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_fanout_1收到信息:{},降级补偿机制",user.getName());
        }
    }

    /**
     * fanout
     */
    @RabbitListener(queues="queue_fanout_2")
    public void receiveQueue2SendFanoutMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_fanout_2收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_fanout_2:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_fanout_2收到信息:{},降级补偿机制",user.getName());
        }
    }

    /**
     * direct
     * bindingkey:aaa
     */
    @RabbitListener(queues="queue_direct_1")
    public void receiveQueue1SendDirectMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_direct_1收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_direct_1:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_direct_1收到信息:{},降级补偿机制",user.getName());
        }
    }

    /**
     * direct
     * bindingkey:bbb
     */
    @RabbitListener(queues="queue_direct_2")
    public void receiveQueue2SendDirectMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_direct_2收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_direct_2:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_fanout_2收到信息:{},降级补偿机制",user.getName());
        }
    }

    /**
     * topic
     * bindingkey:aaa.*
     */
    @RabbitListener(queues="queue_topic_1")
    public void receiveQueue1SendTopicMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_topic_1收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_topic_1:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_topic_1收到信息:{},降级补偿机制",user.getName());
        }
    }

    /**
     * topic
     * bindingkey:aaa.#
     */
    @RabbitListener(queues="queue_topic_2")
    public void receiveQueue2SendTopicMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_topic_2收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_topic_2:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_topic_2收到信息:{},降级补偿机制",user.getName());
        }
    }

    /**
     * topic
     * alternate的正常队列
     * bindingkey:aaa
     */
    @RabbitListener(queues="queue_alt_direct")
    public void receiveQueueSendAltTopicMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_alt_direct收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_alt_direct:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_alt_direct收到信息:{},降级补偿机制",user.getName());
        }
    }

    /**
     * fanout
     * alternate的正常队列的备份队列
     */
    @RabbitListener(queues="queue_alt_fanout")
    public void receiveQueueSendAltFanoutMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_alt_fanout收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_alt_fanout:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_alt_fanout收到信息:{},降级补偿机制",user.getName());
        }
    }

    /**
     * ttl+dlx
     * 实现延时队列
     * 队列延时10s
     */
    @RabbitListener(queues="queue_dlx_fanout")
    public void receiveQueueSendDlxFanoutMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_dlx_fanout收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_dlx_fanout:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_dlx_fanout收到信息:{},降级补偿机制",user.getName());
        }
    }

    /**
     * rabbitmq_delayed_message_exchange
     * 实现延时队列
     */
    @RabbitListener(queues="queue_delay")
    public void receiveQueueSendDelayDirectMsg(User user, Message message, Channel channel) throws IOException {
        try {
            log.info("queue_delay收到信息:{}",user.getName());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("queue_delay:接收消息出现异常");
            log.error(e.getMessage(),e);
            //通知队列删除
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //补偿机制
            log.info("queue_delay收到信息:{},降级补偿机制",user.getName());
        }
    }
}
