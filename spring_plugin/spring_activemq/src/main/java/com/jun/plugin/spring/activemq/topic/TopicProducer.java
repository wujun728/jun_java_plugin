/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.activemq.topic.TopicProduct.java
 * Class:			TopicProduct
 * Date:			2012-11-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.jun.plugin.spring.activemq.topic;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * @author Wujun
 *
 */
public class TopicProducer {
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageProducer producer;
	
	public static final String SUBJECT = "msgTopic";
	
	public TopicProducer() {

	}
	
	public void exe() {
		try {
			init();
			producer.send(session.createTextMessage("ni hao!"));
			close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createTopic(SUBJECT);
		
		producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}
	
    // 关闭连接
    private void close() throws JMSException {
        if (producer != null)
            producer.close();
        if (session != null)
            session.close();
        if (connection != null)
            connection.close();
    }

	public static void main(String[] args) {
		new TopicProducer().exe();
	}
}
