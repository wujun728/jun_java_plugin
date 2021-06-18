package com.smq.message;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class EmailMessageConverter implements MessageConverter {

	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		return null;
	}

	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		return null;
	}


}
