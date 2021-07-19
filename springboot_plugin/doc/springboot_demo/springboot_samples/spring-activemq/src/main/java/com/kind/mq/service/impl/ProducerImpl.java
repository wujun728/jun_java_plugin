package com.kind.mq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kind.mq.domain.Mail;
import com.kind.mq.service.Producer;
@Transactional
@Service("producer")
public class ProducerImpl implements Producer{
	
	@Autowired
	@Qualifier("jmsTemplate")
	private JmsTemplate jmsTemplate;
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void sendMail(Mail mail) {
		jmsTemplate.convertAndSend(mail);
	}

}
