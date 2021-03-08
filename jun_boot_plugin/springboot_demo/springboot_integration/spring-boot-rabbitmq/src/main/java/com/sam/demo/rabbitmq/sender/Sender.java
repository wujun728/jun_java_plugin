package com.sam.demo.rabbitmq.sender;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.sam.demo.rabbitmq.message.SendData;

@Component
public class Sender implements RabbitTemplate.ConfirmCallback, ReturnCallback {
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	private Random random = new Random();
	
	@PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {  
            //System.out.println("消息发送成功:" + correlationData + "," + cause);  
        } else {  
            System.out.println("消息发送失败:" + correlationData + "," + cause);  
        }  
		
	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		System.out.println(routingKey + "," + exchange + "," + replyText + replyCode + " 发送失败");
		
	}

	

    public void send(String msg){
    	int i = random.nextInt(2);
    	CorrelationData correlationId = new CorrelationData(msg);
    	
    	
    	System.out.println("开始向fanoutExchange发送消息 : " + msg.toLowerCase());
    	SendData data = new SendData();
    	data.setId(100);
    	data.setContent(msg.toLowerCase());
    	data.setTime(new Date());
    	// 转换消息并发送
    	rabbitTemplate.convertAndSend("fanoutExchange", null, data, correlationId);
    	System.out.println("结束向fanoutExchange发送消息 : " + msg.toLowerCase());
    	System.out.println("--------------------------");

    	
//    	SendData data = new SendData();
//    	data.setContent(msg);
//    	data.setId(i);
//    	data.setTime(new Date());
//    	// 转换消息并发送,并且获取消费者返回值
//    	System.out.println("开始向topicExchange发送消息 : " + msg.toLowerCase() + ",routingKey=key." + i);
//    	rabbitTemplate.convertAndSend("topicExchange", "key." + i, data, correlationId);
//    	System.out.println("结束向topicExchange发送消息 : " + msg.toLowerCase() + ",routingKey=key." + i);
//    	System.out.println("------------------------------------------------");
    }
}
