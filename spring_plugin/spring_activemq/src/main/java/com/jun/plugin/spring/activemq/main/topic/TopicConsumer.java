/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.activemq.topic.TopicConsumer.java
 * Class:			TopicConsumer
 * Date:			2012-11-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.jun.plugin.spring.activemq.main.topic;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-11-26 下午3:15:05 
 */

public class TopicConsumer implements MessageListener {
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageConsumer consumer;
	
	public static final String SUBJECT = "msgTopic";
	
	private String name;
	
	public TopicConsumer(String name) {
		this.name = name;
	}
	
	public void exe() {
		try {
			init();
			connection.start();
			
			Thread.sleep(100000);
			close();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createTopic(SUBJECT);
		consumer = session.createConsumer(destination);
		
		consumer.setMessageListener(this);
	}

	/**   
	 * @param message  
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)  
	 */
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			try {
				System.out.println(name + ":" + msg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("receive error!");
		}
	}
	
    // 关闭连接
    private void close() throws JMSException {
        if (consumer != null)
        	consumer.close();
        if (session != null)
            session.close();
        if (connection != null)
            connection.close();
    }

	public static void main(String[] args) {
		new TopicConsumer("topic1").exe();
		new TopicConsumer("topic2").exe();
	}
}
