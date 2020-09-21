/**
 * IRabbitmqListener.java
 * Created at 2016-11-17
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.amqp.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 : IRabbitmqListener
 *
 * @author wangkang
 */
public interface IRabbitmqListener<T> {

    /**
     * 描述 : 消费者队列(Bean)
     *
     * @return Queue
     */
    Queue queue();

    /**
     * 描述 : 死信消费者队列(Bean)
     *
     * @return Queue
     */
    Queue dlxQueue();

    /**
     * 描述 : 绑定(Bean)
     *
     * @return Binding
     */
    Binding binding();

    /**
     * 描述 : 死信绑定(Bean)
     *
     * @return Binding
     */
    Binding dlxBinding();

    /**
     * 描述 : 处理消息
     *
     * @param message 消息
     */
    void process(RabbitmqMessage<T> message);

    /**
     * queue
     *
     * @param queueName       queueName
     * @param dlxExchangeName dlxExchangeName
     * @param dlxRoutingkey   dlxRoutingkey
     * @return Queue
     */
    default Queue queue(String queueName, String dlxExchangeName, String dlxRoutingkey) {
        Map<String, Object> args = new HashMap<>();
        args.put(RabbitmqConstant.X_DEAD_LETTER_EXCHANGE, dlxExchangeName);
        args.put(RabbitmqConstant.X_DEAD_LETTER_ROUTING_KEY, dlxRoutingkey);
        return new Queue(queueName, true, false, false, args);
    }

    /**
     * dlxQueue
     *
     * @param queueName queueName
     * @return Queue
     */
    default Queue dlxQueue(String queueName) {
        return new Queue(queueName);
    }


    /**
     * binding
     *
     * @param queueName    queueName
     * @param exchangeName exchangeName
     * @param routingkey   routingkey
     * @return Binding
     */
    default Binding binding(String queueName, String exchangeName, String routingkey) {
        return new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingkey, null);
    }

    /**
     * dlxBinding
     *
     * @param queueName    queueName
     * @param exchangeName exchangeName
     * @param routingkey   routingkey
     * @return Binding
     */
    default Binding dlxBinding(String queueName, String exchangeName, String routingkey) {
        return new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingkey, null);
    }

}
