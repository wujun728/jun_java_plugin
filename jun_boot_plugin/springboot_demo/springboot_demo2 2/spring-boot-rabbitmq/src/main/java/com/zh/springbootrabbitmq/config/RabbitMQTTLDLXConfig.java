package com.zh.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitMQ TTL DLX配置类
 * TTL:死信,消息在队列里的生存时间，到期即成为死信。
 * 可以针对队列设置生存时间也可以针对每一条消息设置生存时间
 * 不过这里不建议在生产者里对每一条消息设置生存时间，
 * 因为这是一个坑，如果同一个队列里的消息生存时间不一样
 * 先入队的消息生存时间长会阻塞后入队生存时间短的消息转发到死信队列
 * DLX：死信交换机，通常可以将到期的死信转发到死信交换机，
 * 然后死信交换机将消息路由到死信队列消费达到延时队列的目的
 *
 * @author Wujun
 * @date 2019/7/9
 */
@Configuration
public class RabbitMQTTLDLXConfig {

    @Bean
    public FanoutExchange ttlFanoutExchange(){
        return new FanoutExchange("exchange_ttl_fanout");
    }

    @Bean
    public Queue ttlFanoutQueue(){
        Map<String,Object> arg = new HashMap<>(16);
        arg.put("x-message-ttl", 10000);
        arg.put("x-dead-letter-exchange", "exchange_dlx_fanout");
        return new Queue("queue_ttl_fanout",true,false,false,arg);
    }

    @Bean
    public Binding ttlFanoutBinding(Queue ttlFanoutQueue, FanoutExchange ttlFanoutExchange){
        return BindingBuilder.bind(ttlFanoutQueue).to(ttlFanoutExchange);
    }

    @Bean
    public FanoutExchange dlxFanoutExchange(){
        return new FanoutExchange("exchange_dlx_fanout");
    }

    @Bean
    public Queue dlxFanoutQueue(){
        return new Queue("queue_dlx_fanout");
    }

    @Bean
    public Binding dlxFanoutBinding(Queue dlxFanoutQueue, FanoutExchange dlxFanoutExchange){
        return BindingBuilder.bind(dlxFanoutQueue).to(dlxFanoutExchange);
    }

}
