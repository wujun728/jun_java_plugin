package com.kind.mq.listener;

import org.springframework.transaction.annotation.Transactional;

import com.kind.mq.domain.Mail;
public class QueueListener {
	@Transactional
	public void displayMail(Mail mail) {
		System.out.println("从ActiveMQ队列myqueue中取出一条消息：");
		System.out.println(mail.toString());
		}
}
