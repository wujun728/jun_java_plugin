package com.jun.plugin.spring.activemq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/** 
 * 
 * @author Wujun
 * 队列消费，返回消息
 */
public class JMSConsumer {

	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER; 
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD; 
	private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL;
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;  
		Connection connection = null;  
		Session session;  
		Destination destination;  
		MessageConsumer messageConsumer;  
		
		connectionFactory=new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);
				
		try {
			connection=connectionFactory.createConnection();  
			connection.start();
			session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);  
			destination=session.createQueue("FirstQueue1");  
			messageConsumer=session.createConsumer(destination);  
			while(true){
				TextMessage textMessage=(TextMessage)messageConsumer.receive(100000);
				if(textMessage!=null){
					System.out.println("队列创建成功FirstQueue1："+textMessage.getText());
				}else{
					break;
				}
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
