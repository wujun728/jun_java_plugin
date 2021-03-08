package com.zh.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitMQ备份交换机配置类
 * 当生产者将消息发送到交换机上时，
 * 加入交换机通过routingkey无法匹配到绑定了他的队列的bingingkey时
 * 为了消息不丢失，指定备份交换机，这样消息可以路由到备份交换机上，
 * 在路由到队列里消费，通常备份交换机是fanout类型
 * @author Wujun
 * @date 2019/7/9
 */
@Configuration
public class RabbitMQAlternateExchangeConfig {

    private static final String BINDING_KET_AAA = "aaa";

    @Bean
    public DirectExchange altDirectExchange(){
        Map<String,Object> arg = new HashMap<>(16);
        arg.put("alternate-exchange","exchange_alt_fanout");
        return new DirectExchange("exchange_alt_direct",true,false,arg);
    }

    @Bean
    public Queue altDirectQueue(){
        return new Queue("queue_alt_direct");
    }


    @Bean
    public Binding altDirectBinding(Queue altDirectQueue, DirectExchange altDirectExchange){
        return BindingBuilder.bind(altDirectQueue).to(altDirectExchange).with(BINDING_KET_AAA);
    }

    @Bean
    public FanoutExchange altFanoutExchange(){
        return new FanoutExchange("exchange_alt_fanout");
    }

    @Bean
    public Queue altFanoutQueue(){
        return new Queue("queue_alt_fanout");
    }

    @Bean
    public Binding altFanoutBinding(Queue altFanoutQueue, FanoutExchange altFanoutExchange){
        return BindingBuilder.bind(altFanoutQueue).to(altFanoutExchange);
    }

}
