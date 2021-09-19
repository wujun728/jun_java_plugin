

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;

public class Mail_1_normal {

	// ͨ����뷢���ʼ�
	@Test
	public void testMail() throws Exception {
	
		// ���������ʼ��������Ĳ���
		Properties prop = new Properties();
		prop.put("mail.transport.protocol", "smtp");	// ָ��Э��
		prop.put("mail.smtp.host", "localhost");		// ����   stmp.qq.com
		prop.put("mail.smtp.port", 25);					// �˿�
		prop.put("mail.smtp.auth", "true");				// �û�������֤
		prop.put("mail.debug", "true");					// ����ģʽ
		
		
		//1. �����ʼ��Ự����
		Session session = Session.getDefaultInstance(prop);
		
		//2. �����ʼ�����
		MimeMessage message = new MimeMessage(session);
		//3. �����ʼ��������: �����ˡ��ռ��ˡ����⡢���ݡ�����ʱ��
		message.setSender(new InternetAddress("zhangsan@itcast.com"));
		message.setRecipient(RecipientType.TO, new InternetAddress("lisi@itcast.com"));
		message.setSubject("�ҵĵ�һ���ʼ�2��");
		message.setText("�ʼ�����2��");
		message.setSentDate(new Date());
		message.saveChanges(); // �����ʼ�
		
		//4. �ʼ�����
		Transport trans = session.getTransport();
		trans.connect("zhangsan", "888"); // ָ�����ӷ����˵��û�����
		trans.sendMessage(message, message.getAllRecipients());
		trans.close();// �Ͽ�����
	}

}
