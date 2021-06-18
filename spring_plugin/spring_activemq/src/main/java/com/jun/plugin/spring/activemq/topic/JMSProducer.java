package com.jun.plugin.spring.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/** 
 * 
 * @author Wujun
 *
 */
public class JMSProducer {

	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER;  
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;  
	private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL;  
	private static final int SENDNUM=10; 
	
	public static void main(String[] args) {
		
		ConnectionFactory connectionFactory; 
		Connection connection = null; 
		Session session;  
		Destination destination;  
		MessageProducer messageProducer; 
		
		connectionFactory=new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		
		try {
			connection=connectionFactory.createConnection(); 
			connection.start();  
			session=connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); 
			// destination=session.createQueue("FirstQueue1"); 
			destination=session.createTopic("FirstTopic1");
			messageProducer=session.createProducer(destination);  
			sendMessage(session, messageProducer);  
			session.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param session
	 * @param messageProducer
	 * @throws Exception
	 */
	public static void sendMessage(Session session,MessageProducer messageProducer)throws Exception{
		for(int i=0;i<JMSProducer.SENDNUM;i++){
			TextMessage message=session.createTextMessage("ActiveMQ Test "+i);
			System.out.println(" abcd "+"ActiveMQ Test "+i);
			messageProducer.send(message);
		}
	}
}
