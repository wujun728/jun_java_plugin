

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;

/**
 * ��ͼƬ��Դ���ʼ�
 * @author AdminTH
 *
 */
public class Mail_2_img {

	@Test
	public void testMail() throws Exception {
	
		// ���������ʼ��������Ĳ���
		Properties prop = new Properties();
		prop.put("mail.transport.protocol", "smtp");	// ָ��Э��
		prop.put("mail.smtp.host", "localhost");		// ����   stmp.qq.com
		prop.put("mail.smtp.port", 25);					// �˿�
		prop.put("mail.smtp.auth", "true");				// �û�������֤
		prop.put("mail.debug", "true");					// ����ģʽ
		
		//1. �����Ự
		Session session = Session.getDefaultInstance(prop);
		//2. �����ʼ�����
		MimeMessage message = new MimeMessage(session);
		//3. �����ʼ�����
		message.setSender(new InternetAddress("zhangsan@itcast.com"));
		message.setRecipient(RecipientType.TO, new InternetAddress("lisi@itcast.com"));
		message.setSubject("��ͼƬ���ʼ�");
		message.setSentDate(new Date());
		//message.setText("<a href='#'>�ٶ�<a/>");
		
		/**************1. �ʼ�����ͼƬ��Դ********************/
		// 1.1 �����������ʼ���
		MimeMultipart mul = new MimeMultipart("related");
		// �ʼ���related  = ���� +  ��Դ
		MimeBodyPart content = new MimeBodyPart(); // ����
		MimeBodyPart resource = new MimeBodyPart();  // ��Դ
		// ��������Դ���õ������ʼ���
		mul.addBodyPart(content);
		mul.addBodyPart(resource);
		
		//---- �����ʼ���Դ------
		String path = this.getClass().getResource("1.jpg").getPath();
		
		File file = new File(path);
		DataSource ds = new FileDataSource(file); 
		DataHandler handler = new DataHandler(ds);
		resource.setDataHandler(handler);  // ���������Դ
		resource.setContentID("1.jpg");  // ͼƬ��Դ���ʼ��е����
		
		//---- �����ʼ�����------
		content.setContent("<img src='cid:1.jpg' />�����ʼ����㿴ͼƬ��", "text/html;charset=UTF-8");
		
		// 1.2 �Ѹ����ʼ������õ��ʼ�������(message)��
		message.setContent(mul);
		
		message.saveChanges(); // �����ʼ�
		//4. �ʼ�����
		Transport trans = session.getTransport();
		trans.connect("zhangsan", "888"); // ָ�����ӷ����˵��û�����
		trans.sendMessage(message, message.getAllRecipients());
		trans.close();// �Ͽ�����
	}

}














