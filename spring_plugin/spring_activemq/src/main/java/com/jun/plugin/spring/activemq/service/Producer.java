package com.jun.plugin.spring.activemq.service;

import com.jun.plugin.spring.activemq.po.Mail;


public interface Producer {
	public void sendMail(Mail mail);
}
