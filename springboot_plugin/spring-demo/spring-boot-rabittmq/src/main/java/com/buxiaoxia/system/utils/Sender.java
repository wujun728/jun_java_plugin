package com.buxiaoxia.system.utils;

import com.buxiaoxia.system.config.RabittmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by xw on 2017/3/25.
 * 2017-03-25 17:04
 */
@Component
public class Sender implements RabbitTemplate.ConfirmCallback {

	private RabbitTemplate rabbitTemplate;

	/**
	 * 配置发送消息的rabbitTemplate，因为是构造方法，所以不用注解Spring也会自动注入（应该是新版本的特性）
	 *
	 * @param rabbitTemplate
	 */
	public Sender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		//设置消费回调
		this.rabbitTemplate.setConfirmCallback(this);
	}


	public String send1(String msg) {
		String uuid = UUID.randomUUID().toString();
		CorrelationData correlationId = new CorrelationData(uuid);
		rabbitTemplate.convertAndSend(RabittmqConfig.EXCHANGE, RabittmqConfig.ROUTINGKEY1, msg,
				correlationId);
		return null;
	}


	public String send2(String msg) {
		String uuid = UUID.randomUUID().toString();
		CorrelationData correlationId = new CorrelationData(uuid);
		rabbitTemplate.convertAndSend(RabittmqConfig.EXCHANGE, RabittmqConfig.ROUTINGKEY2, msg,
				correlationId);
		return null;
	}

	/**
	 * 消息的回调，主要是实现RabbitTemplate.ConfirmCallback接口
	 * 注意，消息回调只能代表成功消息发送到RabbitMQ服务器，不能代表消息被成功处理和接受
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println(" 回调id:" + correlationData);
		if (ack) {
			System.out.println("消息发送成功");
		} else {
			System.out.println("消息发送失败:" + cause + "\n重新发送");

		}
	}
}
