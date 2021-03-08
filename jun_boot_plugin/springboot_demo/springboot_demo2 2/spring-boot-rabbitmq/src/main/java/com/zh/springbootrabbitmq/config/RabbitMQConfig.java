package com.zh.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ配置类
 * rabbitmq交换机有4种：fanout，direct，topic，header
 * header用的少，这里略
 * @author Wujun
 * @date 2019/7/9
 */
@Configuration
public class RabbitMQConfig {

    private static final String BINDING_KET_AAA = "aaa";
    private static final String BINDING_KET_BBB = "bbb";
    private static final String BINDING_KET_AAABBB = "aaa.*";
    private static final String BINDING_KET_AAABBBCCC = "aaa.#";

    /**
     * FanoutExchange
     * 无需bindingkey
     * 只要将队列绑在交换机上，
     * 队列就会将消息路由到这些队列里，
     * 比较像activemq的topic
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("exchange_fanout");
    }

    @Bean
    public Queue fanoutQueue1(){
        return new Queue("queue_fanout_1");
    }

    @Bean
    public Queue fanoutQueue2(){
        return new Queue("queue_fanout_2");
    }

    @Bean
    public Binding fanoutBinding1(Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutBinding2(Queue fanoutQueue2, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    /**
     * 需要bindingkey
     * 队列绑定交换机时需要指定bindingkey，
     * 交换机路由消息时根据routingkey完全匹配bingingkey，
     * 然后把消息路由给匹配上队列
     * 比较像activemq的p2p
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("exchange_direct");
    }

    @Bean
    public Queue directQueue1(){
        return new Queue("queue_direct_1");
    }

    @Bean
    public Queue directQueue2(){
        return new Queue("queue_direct_2");
    }

    @Bean
    public Binding directBinding1(Queue directQueue1, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue1).to(directExchange).with(BINDING_KET_AAA);
    }

    @Bean
    public Binding directBinding2(Queue directQueue2, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue2).to(directExchange).with(BINDING_KET_BBB);
    }

    /**
     * 需要bindingkey
     * 队列绑定交换机时需要指定bindingkey，
     * 交换机路由消息时根据routingkey通配符匹配bingingkey，
     * bingingkey通常以"."的形式分隔单次，
     * routingkey通常以"*"代表匹配一个单词
     * 以"#"匹配一个或多个单词
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("exchange_topic");
    }

    @Bean
    public Queue topicQueue1(){
        return new Queue("queue_topic_1");
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue("queue_topic_2");
    }

    @Bean
    public Binding topicBinding1(Queue topicQueue1, TopicExchange topicExchange){
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with(BINDING_KET_AAABBB);
    }

    @Bean
    public Binding topicBinding2(Queue topicQueue2, TopicExchange topicExchange){
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with(BINDING_KET_AAABBBCCC);
    }

}
