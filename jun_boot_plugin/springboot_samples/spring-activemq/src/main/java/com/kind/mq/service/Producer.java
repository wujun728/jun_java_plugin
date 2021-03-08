package com.kind.mq.service;

import com.kind.mq.domain.Mail;


public interface Producer {
	public void sendMail(Mail mail);
}
