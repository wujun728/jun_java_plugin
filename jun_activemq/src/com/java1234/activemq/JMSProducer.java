package com.java1234.activemq;

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
 * 消息生产者
 * @author Administrator
 *
 */
public class JMSProducer {

	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
	private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL; // 默认的连接地址
	private static final int SENDNUM=10; // 发送的消息数量
	
	public static void main(String[] args) {
		
		ConnectionFactory connectionFactory; // 连接工厂
		Connection connection = null; // 连接
		Session session; // 会话 接受或者发送消息的线程
		Destination destination; // 消息的目的地
		MessageProducer messageProducer; // 消息生产者
		
		// 实例化连接工厂
		connectionFactory=new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		
		try {
			connection=connectionFactory.createConnection(); // 通过连接工厂获取连接
			connection.start(); // 启动连接
			session=connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); // 创建Session
			destination=session.createQueue("FirstQueue1"); // 创建消息队列
			messageProducer=session.createProducer(destination); // 创建消息生产者
			sendMessage(session, messageProducer); // 发送消息
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
	 * 发送消息
	 * @param session
	 * @param messageProducer
	 * @throws Exception
	 */
	public static void sendMessage(Session session,MessageProducer messageProducer)throws Exception{
		for(int i=0;i<JMSProducer.SENDNUM;i++){
			TextMessage message=session.createTextMessage("ActiveMQ 发送的消息"+i);
			System.out.println("发送消息："+"ActiveMQ 发送的消息"+i);
			messageProducer.send(message);
		}
	}
}
