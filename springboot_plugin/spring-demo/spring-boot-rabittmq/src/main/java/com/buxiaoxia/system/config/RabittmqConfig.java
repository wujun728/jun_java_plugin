package com.buxiaoxia.system.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xw on 2017/3/25.
 * 2017-03-25 17:04
 */
@Configuration
public class RabittmqConfig {

	/**
	 * 消息交换机的名字
	 */
	public static final String EXCHANGE = "my-mq-exchange";
	/**
	 * 队列key1
	 */
	public static final String ROUTINGKEY1 = "queue_one_key1";
	/**
	 * 队列key2
	 */
	public static final String ROUTINGKEY2 = "queue_one_key2";


	@Bean
	@ConfigurationProperties("spring.rabbitmq")
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setPublisherConfirms(true); //必须要设置
		return connectionFactory;
	}

	/**
	 * 配置消息交换机
	 * 针对消费者配置
	 * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
	 * HeadersExchange：通过添加属性key-value匹配
	 * DirectExchange: 按照routingkey分发到指定队列
	 * TopicExchange: 多关键字匹配
	 */
	@Bean
	public DirectExchange defaultExchange() {
		return new DirectExchange(EXCHANGE, true, false);
	}


	/**
	 * 配置消息队列1
	 * 针对消费者配置
	 *
	 * @return
	 */
	@Bean
	public Queue queue() {
		return new Queue("queue_one", true); //队列持久

	}

	/**
	 * 将消息队列1与交换机绑定
	 * 针对消费者配置
	 *
	 * @return
	 */
	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(defaultExchange()).with(RabittmqConfig.ROUTINGKEY1);
	}

	/**
	 * 配置消息队列2
	 * 针对消费者配置
	 *
	 * @return
	 */
	@Bean
	public Queue queue1() {
		return new Queue("queue_one1", true); //队列持久

	}

	/**
	 * 将消息队列2与交换机绑定
	 * 针对消费者配置
	 *
	 * @return
	 */
	@Bean
	public Binding binding1() {
		return BindingBuilder.bind(queue1()).to(defaultExchange()).with(RabittmqConfig.ROUTINGKEY2);
	}

	/**
	 * 接受消息的监听，这个监听会接受消息队列1的消息
	 * 针对消费者配置
	 *
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer messageContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
		container.setQueues(queue());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
		container.setMessageListener(new ChannelAwareMessageListener() {

			public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
				byte[] body = message.getBody();
				System.out.println("queue 收到消息 : " + new String(body));
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
			}

		});
		return container;
	}

	/**
	 * 接受消息的监听，这个监听会接受消息队列1的消息
	 * 针对消费者配置
	 *
	 * @return
	 */
//	@Bean
//	public SimpleMessageListenerContainer messageContainer2() {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//		container.setQueues(queue1());
//		container.setExposeListenerChannel(true);
//		container.setMaxConcurrentConsumers(1);
//		container.setConcurrentConsumers(1);
//		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
//		container.setMessageListener(new ChannelAwareMessageListener() {
//
//			public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
//				byte[] body = message.getBody();
//				System.out.println("queue1 收到消息 : " + new String(body));
//				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
//			}
//
//		});
//		return container;
//	}

}
