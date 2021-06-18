package com.jun.plugin.spring.activemq.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;
import com.jun.plugin.spring.activemq.po.Mail;
import com.jun.plugin.spring.activemq.po.Message;
import com.jun.plugin.spring.activemq.service.impl.ProducerImpl;
import com.jun.plugin.spring.activemq.service.impl.TopicImpl;
import com.jun.plugin.spring.activemq.websocket.MyWebSocketHandler;


@Controller
public class ActiveMQController {
	
	MyWebSocketHandler handler=new MyWebSocketHandler();
	
	@Autowired
	ProducerImpl producer;
	@Autowired
	TopicImpl topic;
	@RequestMapping(value="/produce",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public void produce(@ModelAttribute("mail")Mail mail) throws Exception{
			Message msg = new Message();
			msg.setText("向队列myquene添加一条消息:"+mail.toString());
			handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
			producer.sendMail(mail);
	}
	
	@RequestMapping(value="/topic",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public void topic(@ModelAttribute("mail")Mail mail) throws Exception{
		Message msg = new Message();
		msg.setText("向话题mytopic发布一条消息:"+mail.toString());
		handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
		topic.sendMail(mail);
	}
	
	@RequestMapping("demo")
	public String demo(HttpServletRequest request){
		request.getSession().setAttribute("uid", (long)11);
		return "demo";
	}
	
	
	
}
