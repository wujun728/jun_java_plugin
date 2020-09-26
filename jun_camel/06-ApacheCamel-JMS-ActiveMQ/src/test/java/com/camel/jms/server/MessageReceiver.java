package com.camel.jms.server;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.PropertyConfigurator;

import javax.jms.*;

/**
 * 消息接受者
 * 
 * @author CYX
 * @time 2016年12月13日下午4:27:31
 */
public class MessageReceiver {

	/** tcp地址 */
//	public static final String BROKER_URL = "tcp://localhost:61616";
	public static final String BROKER_URL = "tcp://localhost:61616";
	/** 目标 */
	public static final String DESTINATION = "hoo.mq.queue";

	public static void main(String[] args) throws Exception {
		
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);
		
		MessageReceiver.run();
	}

	public static void run() throws Exception {

		Connection connection = null;
		Session session = null;

		try {
			// 创建链接工厂
			ConnectionFactory factory = new ActiveMQConnectionFactory("user",
					"123", BROKER_URL);
			// 通过工厂创建一个连接
			connection = factory.createConnection();
			// 启动链接
			connection.start();
			// 创建一个session会话.
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列.
			Destination destination = session.createQueue(DESTINATION);
			// 创建消息制作者.
			MessageConsumer consumer = session.createConsumer(destination);

			while (true) {
				// 接收数据的时间(等待)100ms
				Message message = consumer.receive();

				if (message instanceof TextMessage) {
					TextMessage txtMsg = (TextMessage) message;
					System.out.println("Received Text message : " + txtMsg.getText());
				} else if (message != null) {
					BytesMessage bytesMsg = (BytesMessage) message;
					byte[] bytes = new byte[(int) bytesMsg.getBodyLength()];
					bytesMsg.readBytes(bytes);
					System.out.println("Received byte message: " + new String(bytes));
				}

				// TextMessage text = (TextMessage) message;
				// if (null != text) {
				// System.out.println("text : " + text.getText());
				// System.out.println("接收到的 : " + text);
				// } else {
				// break;
				// }
			}
			// 提交会话.
			// session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != session) {
				session.close();
			}
			if (null != connection) {
				connection.close();
			}
		}

	}

}
