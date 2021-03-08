package com.sam.demo;


import java.util.Date;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@SuppressWarnings({"unchecked","rawtypes"})
@Component
public class Sender {
	
	@Autowired
	private KafkaTemplate kafkaTemplate;
	private Gson gson = new GsonBuilder().create();
	
	
	public void sendMessage(){
		Message m = new Message();
		m.setId(System.currentTimeMillis());
		m.setMsg(UUID.randomUUID().toString());
		m.setSendTime(new Date());
		kafkaTemplate.send("test2", m.getMsg(), gson.toJson(m));
		System.out.println(m.getId() + " , " + m.getMsg());
	}
}