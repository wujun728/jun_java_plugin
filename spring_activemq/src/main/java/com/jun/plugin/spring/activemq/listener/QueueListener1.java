package com.jun.plugin.spring.activemq.listener;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;
import com.jun.plugin.spring.activemq.po.Mail;
import com.jun.plugin.spring.activemq.po.Message;
import com.jun.plugin.spring.activemq.websocket.MyWebSocketHandler;
public class QueueListener1 {
	MyWebSocketHandler handler=new MyWebSocketHandler();
	@Transactional
	public void displayMail(Mail mail) throws Exception {
		Message msg = new Message();
		msg.setText("我是队列消费者1号，我从ActiveMQ队列myqueue中取出一条消息："+mail.toString());
		handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
		}
}
