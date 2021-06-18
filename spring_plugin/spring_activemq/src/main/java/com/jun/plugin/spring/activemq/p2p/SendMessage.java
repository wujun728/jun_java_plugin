package com.jun.plugin.spring.activemq.p2p;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author Wujun
 * 生产者，createQueue
 */
public class SendMessage {
	private String user = ActiveMQConnection.DEFAULT_USER;

    private String password = ActiveMQConnection.DEFAULT_PASSWORD;

    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private String subject = "studentQueue";

    private Destination destination = null;

    private Connection connection = null;

    private Session session = null;

    private MessageProducer producer = null;

    private static final Log log = LogFactory.getLog(SendMessage.class);

    // 初始化
    private void initialize() throws JMSException, Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(subject);
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    // 发送消息
    public void produceMessage() throws JMSException, Exception {
        initialize();
        Student student = new Student("zhansan",25,"male","深圳");
        String msg = null;
        int i = 0 ;
        //while(true){
        	msg = "hello，第" + i++ + "次，ActiveMQ";
        	student.setMsg(msg);
            ObjectMessage objectMessage = session.createObjectMessage(student);
            log.info("Producer:->Sending message: " + student.toString());
            producer.send(objectMessage);
            log.info("Producer:->Message sent complete!");
            //Thread.sleep(5000);
        //}
    }

    // 关闭连接
    public void close() throws JMSException {
        log.info("Producer:->Closing connection");
        if (producer != null)
            producer.close();
        if (session != null)
            session.close();
        if (connection != null)
            connection.close();
    }
	 
    public static void main(String[] args) throws JMSException, Exception {
        SendMessage sendMessage = new SendMessage();
        sendMessage.produceMessage();
        sendMessage.close();
    }
}
