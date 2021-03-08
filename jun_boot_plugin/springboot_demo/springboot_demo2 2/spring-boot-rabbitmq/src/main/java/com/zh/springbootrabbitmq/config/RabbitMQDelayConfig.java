package com.zh.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitMQ延时队列配置类
 * 之前提到了使用TTL+DLX方式实现延时队列
 * 但是对于给每一条消息设置生存时间的话很鸡肋，
 * 因为先入队生存时间长的消息会阻塞后入队生存时间短的消息变成死信转发
 * 所以这里是使用rabbitmq_delayed_message_exchange的方式实现为每一条消息设置生存时间而且不会互相阻塞
 * 这是一个官方提供的插件，3.6及以上版本才能使用，可以去官网下载插件：
 * http://www.rabbitmq.com/community-plugins.html
 * 使用方法：https://blog.csdn.net/eumenides_/article/details/86027185
 * @author Wujun
 * @date 2019/7/9
 */
@Configuration
public class RabbitMQDelayConfig {

    private static final String BINDING_KET_AAA = "aaa";

    @Bean
    public CustomExchange delayExchange(){
        Map<String, Object> arg = new HashMap<>(16);
        //这里可以指定队列类型，为了方便就制定direct类型
        arg.put("x-delayed-type", "direct");
        return new CustomExchange("exchange_delay","x-delayed-message",true,false,arg);
    }

    @Bean
    public Queue delayQueue(){
        return new Queue("queue_delay");
    }

    @Bean
    public Binding delayBinding(Queue delayQueue, CustomExchange delayExchange){
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(BINDING_KET_AAA).noargs();
    }

}
