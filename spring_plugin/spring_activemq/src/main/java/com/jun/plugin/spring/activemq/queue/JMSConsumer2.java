package com.jun.plugin.spring.activemq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/** 
 * 
 * @author Wujun
 * 队列消费，带监听的
 */
public class JMSConsumer2 implements MessageListener {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;  
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; 
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;  
	
	 
	public static void main(String[] args) {
		ConnectionFactory connectionFactory; 
		Connection connection = null; 
		Session session; 
		Destination destination; 
		MessageConsumer messageConsumer;

		connectionFactory = new ActiveMQConnectionFactory(JMSConsumer2.USERNAME, JMSConsumer2.PASSWORD,
				JMSConsumer2.BROKEURL);

		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("FirstQueue1");
			messageConsumer = session.createConsumer(destination);
			messageConsumer.setMessageListener(new JMSConsumer2());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			System.out.println("队列创建成功：" + ((TextMessage) message).getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}