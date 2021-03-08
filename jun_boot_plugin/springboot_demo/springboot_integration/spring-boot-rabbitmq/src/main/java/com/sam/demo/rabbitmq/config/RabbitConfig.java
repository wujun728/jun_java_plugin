package com.sam.demo.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	
	@Bean
	public Jackson2JsonMessageConverter customConverter() {
	  Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
	  return converter;
	}
	
//	@Bean
//	public Queue topicQueue0() {
//		return new Queue("sam.topicQueue0", true); // 队列持久化
//	}
//	@Bean
//	public Queue topicQueue1() {
//		return new Queue("sam.topicQueue1", true); // 队列持久化
//	}
//	@Bean
//	public Queue topicQueue2() {
//		return new Queue("sam.topicQueue2", true); // 队列持久化
//	}
//	
//	/**
//	 * topic队列绑定，将topicQueue都绑定到topicExchange上，
//	 * 消息发送至topicExchange时，监听该队列的消费者中只有1个能消费消息，从而实现消息广播的功能
//	 * @return
//	 */
//	@Bean
//	public Binding topicBinding0() {
//		return BindingBuilder.bind(topicQueue0()).to(topicExchange()).with("key.0");
//	}
//	@Bean
//	public Binding topicBinding1() {
//		return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("key.1");
//	}
//	@Bean
//	public Binding topicBinding2() {
//		return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("key.2");
//	}
	
	
	
	
	
	
	
	
	/**
	 * ------------------------------------------------------------------------------
	 * @return
	 */
	
//	@Bean
//	public TopicExchange topicExchange() {
//        return new TopicExchange("topicExchange");
//    }
//	
//	@Bean
//	public Queue fanoutQueue0() {
//		return new Queue("sam.fanoutQueue0", true); // 队列持久化
//	}
//	
//	@Bean
//	public Queue fanoutQueue1() {
//		return new Queue("sam.fanoutQueue1", true); // 队列持久化
//	}
//	@Bean
//	public FanoutExchange fanoutExchange() {
//        return new FanoutExchange("fanoutExchange");
//    }
//
//	/**
//	 * fanout队列绑定，将fanoutQueue1，fanoutQueue2都绑定到fanoutExchange上，消息发送至fanoutExchange时，2个队列都能接收到消息.
//	 * fanoutQueue1，fanoutQueue2各自有自己的消费者，从而实现消息广播的功能
//	 * @return
//	 */
//	@Bean
//	public Binding fanoutbinding0() {
//		return BindingBuilder.bind(fanoutQueue0()).to(fanoutExchange());
//	}
//	
//	@Bean
//	public Binding fanoutbinding1() {
//		return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
//	}
	
}
