package com.jun.plugin.spring.activemq.listener;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;
import com.jun.plugin.spring.activemq.po.Mail;
import com.jun.plugin.spring.activemq.po.Message;
import com.jun.plugin.spring.activemq.websocket.MyWebSocketHandler;

public class TopicListener2 {
	MyWebSocketHandler handler=new MyWebSocketHandler();
	@Transactional
	public void displayTopic(Mail mail) throws IOException {
		Message msg = new Message();
		msg.setText("我是话题订阅者2号，我从ActiveMQ的Topic：mytopic中取出一条消息："+mail.toString());
		handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
		}
}
