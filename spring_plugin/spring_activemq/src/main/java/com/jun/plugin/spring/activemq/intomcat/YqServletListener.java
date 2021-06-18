package com.jun.plugin.spring.activemq.intomcat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class YqServletListener extends HttpServlet implements MessageListener {

	/** 描述  */
	private static final long serialVersionUID = -3004324312612205638L;
	
	/**   
	 * @throws ServletException  
	 * @see javax.servlet.GenericServlet#init()  
	 */
	@Override
	public void init() throws ServletException {
		try {
			InitialContext initialContext = new InitialContext();
			Context envContext = (Context)initialContext.lookup("java:comp/env");
			
			ConnectionFactory connectionFactory = (ConnectionFactory)envContext.lookup("jms/FailoverConnectionFactory");
			Connection connection = connectionFactory.createConnection();
			
			Session jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = (Destination)envContext.lookup("jms/queue/MyQueue");
			MessageConsumer consumer = jmsSession.createConsumer(destination);
			
			consumer.setMessageListener(this);
			connection.start();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}		
	}

	/**   
	 * @param arg0  
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)  
	 */
	@Override
	public void onMessage(Message message) {
		try {
			if (message.getStringProperty("username").equals("yao")) {
				System.out.println("Yes, yq's Queue is sended success!");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
