package com.camel.jms.server;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息发送者<br>
 * 使用JMS方式发送接收消息<br>
 * @author CYX
 * @time 2016年12月13日下午4:13:58
 */
public class MessageSender {

	/** 发送次数 */
	public static final int SEND_NUM = 5;
	/** tcp地址 */
	public static final String BROKER_URL = "tcp://localhost:61616";
	/** 目标 */
	public static final String DESRINATION = "hoo.mq.queue";

	public static void main(String[] args) {
		try {
			MessageSender.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param producer
	 * @throws Exception
	 */
	public static void sendMessage(Session session, MessageProducer producer) throws Exception {
		for (int i = 0; i < SEND_NUM; i++) {
			String message = "我是消息 : " + (i + 1) + "....";
			TextMessage text = session.createTextMessage(message);

			System.out.println("message : " + message);
			producer.send(text);

		}
	}

	public static void run() throws Exception {

		Connection connection = null;
		Session session = null;

		try {
			// 创建链接工厂.
			ConnectionFactory factory = new ActiveMQConnectionFactory("user", "123", BROKER_URL);
			// 通过工厂创建一个链接.
			connection = factory.createConnection();
			// 启动链接.
			connection.start();
			// 创建一个session会话.
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列.
			Destination destination = session.createQueue(DESRINATION);
			// 创建消息制作者.
			MessageProducer producer = session.createProducer(destination);
			// 设置持久化模式
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			sendMessage(session, producer);
			// 提交会话.
			session.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

}
