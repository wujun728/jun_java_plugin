package com.sam.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class Receiver {

	private Gson gson = new GsonBuilder().create();

	private Logger logger = LoggerFactory.getLogger(getClass());

	@KafkaListener(topics = "test2")
	public void processMessage(String content, Acknowledgment ack) {
		Message m = gson.fromJson(content, Message.class);
		logger.info("{}", m.getMsg());
		ack.acknowledge();
	}
}