package com.jun.plugin.spring.activemq.main.p2p;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * activeMQ:http://localhost:8161/admin/queues.jsp
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-11-26 下午2:57:51
 */
public class ReceiveMessage implements MessageListener {
	private String user = ActiveMQConnection.DEFAULT_USER;

	private String password = ActiveMQConnection.DEFAULT_PASSWORD;

	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	private String subject = "studentQueue";

	private Destination destination = null;

	private Connection connection = null;

	private Session session = null;

	private MessageConsumer consumer = null;

	private static final Log log = LogFactory.getLog(ReceiveMessage.class);
	
	private String name;
	
	public ReceiveMessage(String name) {
		this.name = name;
	}

	// 初始化
	private void initialize() throws JMSException, Exception {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				user, password, url);
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(subject);
		consumer = session.createConsumer(destination);
	}

	// 消费消息
	public void consumeMessage() throws JMSException, Exception {
		initialize();
		// 开始监听
		consumer.setMessageListener(this);
		// Message message = consumer.receive();
		connection.start();

		log.info(name + ":->Begin listening...");
	}

	// 关闭连接
	public void close() throws JMSException {
		log.info(name + ":->Closing connection");
		if (consumer != null) {
			consumer.close();
		}
		if (session != null) {
			session.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

	// 消息处理函数
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				String msg = txtMsg.getText();
				log.info(name + ":->Received: " + msg);
			} else if (message instanceof ObjectMessage) {
				ObjectMessage objMessage = (ObjectMessage) message;
				Student student = (Student) objMessage.getObject();
				log.info(name + ":->Received: " + student.toString());
			}
			log.info(name + ":->receive..." + message.toString());

			// receiveMessage.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws JMSException, Exception {
		new ReceiveMessage("receive1").consumeMessage();
		new ReceiveMessage("receive2").consumeMessage();
	}
}
