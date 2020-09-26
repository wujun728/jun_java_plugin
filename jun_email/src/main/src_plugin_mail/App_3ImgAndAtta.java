

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;

/**
 * 3. ��ͼƬ��Դ�Լ��������ʼ�
 * @author Jie.Yuan
 *
 */
public class App_3ImgAndAtta {
	
	// ��ʼ������
	private static Properties prop;
	// ������
	private static InternetAddress sendMan = null;
	static {
		prop = new Properties();
		prop.put("mail.transport.protocol", "smtp");	// ָ��Э��
		prop.put("mail.smtp.host", "localhost");		// ����   stmp.qq.com
		prop.put("mail.smtp.port", 25);					// �˿�
		prop.put("mail.smtp.auth", "true");				// �û�������֤
		prop.put("mail.debug", "true");					// ����ģʽ
		try {
			sendMan = new InternetAddress("zhangsan@itcast.com");
		} catch (AddressException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testSend() throws Exception {
		// 1. �����ʼ��Ự
		Session session = Session.getDefaultInstance(prop);
		// 2. �����ʼ�����
		MimeMessage message = new MimeMessage(session);
		// 3. ���ò�����⡢�����ˡ��ռ��ˡ�����ʱ�䡢����
		message.setSubject("��ͼƬ�ʼ�");
		message.setSender(sendMan);
		message.setRecipient(RecipientType.TO, new InternetAddress("lisi@itcast.com"));
		message.setSentDate(new Date());
		
		/*
		 * ���(ͼƬ)�ʼ�����
		 */
		// ����һ���ܵ��ʼ���
		MimeMultipart mixed = new MimeMultipart("mixed");
		// ---> ���ʼ��죬���õ��ʼ�������
		message.setContent(mixed);
		// ��ࣺ ���ı�+ͼƬ��Դ��
		MimeBodyPart left = new MimeBodyPart();
		// �Ҳࣺ ����
		MimeBodyPart right = new MimeBodyPart();
		// ���õ����ʼ���
		mixed.addBodyPart(left);
		mixed.addBodyPart(right);
		
		/******����********/
		String attr_path = this.getClass().getResource("a.docx").getPath();
		DataSource attr_ds = new FileDataSource(new File(attr_path));
		DataHandler attr_handler = new DataHandler(attr_ds);
		right.setDataHandler(attr_handler);
		right.setFileName("a.docx");
		
		
		/***************�����ʼ�����: �๦���û��ʼ� (related)*******************/
		// 4.1 ����һ���๦���ʼ���
		MimeMultipart related = new MimeMultipart("related");
		// ----> ���õ����ʼ���������
		left.setContent(related);
		
		// 4.2 �����๦���ʼ������� = ����ı� + �Ҳ�ͼƬ��Դ
		MimeBodyPart content = new MimeBodyPart();
		MimeBodyPart resource = new MimeBodyPart();
		
		// ���þ�������: a.��Դ(ͼƬ)
		String filePath = App_3ImgAndAtta.class.getResource("8.jpg").getPath();
		DataSource ds = new FileDataSource(new File(filePath));
		DataHandler handler = new DataHandler(ds);
		resource.setDataHandler(handler);
		resource.setContentID("8.jpg");   // ������Դ��ƣ����������
		
		// ���þ�������: b.�ı�
		content.setContent("<img src='cid:8.jpg'/>  �ù�����", "text/html;charset=UTF-8");
		
		related.addBodyPart(content);
		related.addBodyPart(resource);
		
		
		
		// 5. ����
		Transport trans = session.getTransport();
		trans.connect("zhangsan", "888");
		trans.sendMessage(message, message.getAllRecipients());
		trans.close();
	}
}










