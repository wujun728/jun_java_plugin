

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;

/**
 * 1. ����һ����ͨ�ʼ�
 * @author Wujun
 *
 */
public class App_1SendMail {

	@Test
	public void testSend() throws Exception {
		
		//0. �ʼ�����
		Properties prop = new Properties();
		prop.put("mail.transport.protocol", "smtp");	// ָ��Э��
		prop.put("mail.smtp.host", "localhost");		// ����   stmp.qq.com
		prop.put("mail.smtp.port", 25);					// �˿�
		prop.put("mail.smtp.auth", "true");				// �û�������֤
		prop.put("mail.debug", "true");					// ����ģʽ
		
		//1. ����һ���ʼ��ĻỰ
		Session session = Session.getDefaultInstance(prop);
		//2. �����ʼ������ (����ʼ�����)
		MimeMessage message = new MimeMessage(session);
		//3. �����ʼ������: 
		//3.1 ����
		message.setSubject("�ҵĵ�һ���ʼ�	");
		//3.2 �ʼ�����ʱ��
		message.setSentDate(new Date());
		//3.3 ������
		message.setSender(new InternetAddress("zhangsan@itcast.com"));
		//3.4 ������
		message.setRecipient(RecipientType.TO, new InternetAddress("lisi@itcast.com"));
		//3.5����
		//message.setText("��ã��Ѿ����ͳɹ���  ����....");  // �򵥴��ı��ʼ�
		// �ʼ��к��г�����
		//message.setText("<a href='#'>�ٶ�</a>");
		message.setContent("<a href='#'>�ٶ�</a>", "text/html;charset=UTF-8");
		
		message.saveChanges();   // �����ʼ�(��ѡ)
		
		//4. ����
		Transport trans = session.getTransport();
		trans.connect("zhangsan", "888");
		// �����ʼ�
		trans.sendMessage(message, message.getAllRecipients());
		trans.close();
	}
}














