package com.jun.plugin.spring.activemq.listener;

import org.springframework.transaction.annotation.Transactional;

import com.jun.plugin.spring.activemq.po.Mail;
public class QueueListener {
	@Transactional
	public void displayMail(Mail mail) {
		System.out.println("从ActiveMQ队列myqueue中取出一条消息：");
		System.out.println(mail.toString());
		}
}
