package com.sam.demo.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.demo.rabbitmq.message.SendData;
import com.sam.demo.rabbitmq.message.SendData2;

@Component
public class Receiver {
	private ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * 消费者1A监听sam.topicQueue队列
	 * @param msg
	 * @throws JsonProcessingException 
	 */
	@RabbitListener(queues = "sam.topicQueue")
    public void processMessageA(SendData2 data) throws JsonProcessingException {
		System.out.println("消费者A" + Thread.currentThread().getName() + " 接收到来自sam.topicQueue队列的消息：" + objectMapper.writeValueAsString(data));
		//return msg.toUpperCase();
	}
	@RabbitListener(queues = "sam.topicQueue")
    public void processMessageB(SendData2 data) throws JsonProcessingException {
		System.out.println("消费者B" + Thread.currentThread().getName() + " 接收到来自sam.topicQueue队列的消息：" + objectMapper.writeValueAsString(data));
		//return msg.toUpperCase();
	}
	@RabbitListener(queues = "sam.topicQueue0")
    public void processMessage0(String msg) {
		System.out.println("消费者" + Thread.currentThread().getName() + " 接收到来自sam.topicQueue0队列的消息：" + msg);
		//return msg.toUpperCase();
	}
	@RabbitListener(queues = "sam.topicQueue1")
    public void processMessage1(String msg) {
		System.out.println("消费者" + Thread.currentThread().getName() + " 接收到来自sam.topicQueue1队列的消息：" + msg);
		//return msg.toUpperCase();
	}
	@RabbitListener(queues = "sam.topicQueue2")
    public void processMessage2(String msg) {
		System.out.println("消费者" + Thread.currentThread().getName() + " 接收到来自sam.topicQueue2队列的消息：" + msg);
		//return msg.toUpperCase();
	}
	
	/**
	 * -------------------------------------------------------------------------------------------------------
	 */
	
	/**
	 * 消费者2A监听sam.fanoutQueue1队列，该队列的消息平均分给这2个监听器处理
	 * @param msg
	 */
	@RabbitListener(queues = "sam.fanoutQueue0")
    public void processMessage2A(SendData2 data) {
		System.out.println("消费者2A" + Thread.currentThread().getName() + " 接收到来自sam.fanoutQueue0队列的消息：" + data.getContent());
	}
	
	/**
	 * 消费者2B监听sam.fanoutQueue2队列
	 * @param msg
	 */
	@RabbitListener(queues = "sam.fanoutQueue1")
    public void processMessage2B(SendData data) {
		System.out.println("消费者2B" + Thread.currentThread().getName() + " 接收到来自sam.fanoutQueue1队列的消息：" + data.getContent());
	}
}
