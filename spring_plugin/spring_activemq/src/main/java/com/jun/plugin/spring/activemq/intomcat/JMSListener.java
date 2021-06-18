package com.jun.plugin.spring.activemq.intomcat;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.jms.*;

public class JMSListener extends HttpServlet implements MessageListener {

	private static final long serialVersionUID = 1L;

	/** 初始化jms连接，创建topic监听器 */
	public void init(ServletConfig config) throws ServletException {
		try {
			InitialContext initCtx = new InitialContext();
			Context envContext = (Context) initCtx.lookup("java:comp/env");
			
			// 监听段
			ConnectionFactory connectionFactory = (ConnectionFactory) envContext
					.lookup("jms/FailoverConnectionFactory");
			Connection connection = connectionFactory.createConnection();
			connection.setClientID("MyClient");
			Session jmsSession = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			// 普通消息订阅者，无法接收持久消息
			MessageConsumer consumer = jmsSession
					.createConsumer((Destination) envContext
							.lookup("jms/topic/MyTopic"));
			// 基于Topic创建持久的消息订阅者，前提：
			// Connection必须指定一个唯一的clientId，当前为MyClient TopicSubscriber consumer
			// = jmsSession.createDurableSubscriber((Topic)
			// envContext.lookup("jms/topic/MyTopic"), "MySub");
			consumer.setMessageListener(this);
			connection.start();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void onMessage(Message message) {
		if (checkText(message, "RefreshArticleId") != null) {
			String articleId = checkText(message, "RefreshArticleId");
			System.out.println("接收刷新文章消息，开始刷新文章ID=" + articleId);
		} else if (checkText(message, "RefreshThreadId") != null) {
			String threadId = checkText(message, "RefreshThreadId");
			System.out.println("接收刷新论坛帖子消息，开始刷新帖子ID=" + threadId);
		} else {
			System.out.println("接收普通消息，不做任何处理！");
		}
	}

	private static String checkText(Message m, String s) {
		try {
			return m.getStringProperty(s);
		} catch (JMSException e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
}
