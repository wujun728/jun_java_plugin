package com.kind.mq.listener;

import org.springframework.transaction.annotation.Transactional;

import com.kind.mq.domain.Mail;

public class MailListener {
	@Transactional
	public void displayMail(Mail mail) {
		System.out.println("从ActiveMQ取出一条消息：");
		System.out.println(mail.toString());
		}
}
