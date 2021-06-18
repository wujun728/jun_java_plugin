package com.jun.plugin.activemq;

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
 * ��Ϣ������
 * @author Administrator
 *
 */
public class JMSConsumer2 {

	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER; // Ĭ�ϵ������û���
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD; // Ĭ�ϵ���������
	private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL; // Ĭ�ϵ����ӵ�ַ
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory; // ���ӹ���
		Connection connection = null; // ����
		Session session; // �Ự ���ܻ��߷�����Ϣ���߳�
		Destination destination; // ��Ϣ��Ŀ�ĵ�
		MessageConsumer messageConsumer; // ��Ϣ��������
		
		// ʵ�������ӹ���
		connectionFactory=new ActiveMQConnectionFactory(JMSConsumer2.USERNAME, JMSConsumer2.PASSWORD, JMSConsumer2.BROKEURL);
				
		try {
			connection=connectionFactory.createConnection();  // ͨ�����ӹ�����ȡ����
			connection.start(); // ��������
			session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); // ����Session
			destination=session.createQueue("FirstQueue1");  // �������ӵ���Ϣ����
			messageConsumer=session.createConsumer(destination); // ������Ϣ������
			messageConsumer.setMessageListener(new Listener()); // ע����Ϣ����
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
