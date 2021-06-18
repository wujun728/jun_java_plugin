

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.MimeMessage.RecipientType;

public class Mail2 {

	// �ʼ����Ͳ�����Ϣ
	static Properties prop = new Properties();
	
	static String user = "itcast";
	static String pwd = "888";
	
	// ��ʼ������
	static {
		// Э��
		prop.put("mail.transport.protocol", "smtp");
		// �˿�
		prop.put("mail.smtp.port", "25");
		// ����
		prop.put("mail.smtp.host", "localhost");
		// ��֤
		prop.put("mail.smtp.auth", "true");
		// ����
		prop.put("mail.debug", "true");
	}
	
	//1. �����ʼ�
	public static void sendEmail() throws Exception {
		//�����Ự����
		Session session = Session.getDefaultInstance(prop, new MyAuthenticator(user,pwd));
		//�ʼ�����
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("itcast@it.com"));
		message.setRecipient(RecipientType.CC, new InternetAddress("yuanjie@it.com"));
		message.setSentDate(new Date());
		message.setSubject("������..............");
		message.setText("��Щ��ȥ������");
		message.saveChanges();
		
		// ����
		Transport.send(message);
	}
	//2. ����html�ʼ�
	public static void sendHtmlEmail() throws Exception {
		//�����Ự����
		Session session = Session.getDefaultInstance(prop, new MyAuthenticator(user,pwd));
		//�ʼ�����
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("itcast@it.com"));
		message.setRecipient(RecipientType.CC, new InternetAddress("yuanjie@it.com"));
		message.setSentDate(new Date());
		message.setSubject("������..............");
		message.setContent("<a href='http://www.baidu.com'>����</a>", "text/html;charset=GBK");
		message.saveChanges();
		// ����
		Transport.send(message);
	}
	//3. ����html + img �ʼ�
	public static void sendHtmlAndImgEmail() throws Exception {
		// �Ự����
		Session session = Session.getDefaultInstance(prop, new MyAuthenticator(user, pwd));
		// �ʼ�����
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("itcast@it.com"));
		message.setRecipient(RecipientType.TO, new InternetAddress("yuanjie@it.com"));
		message.setSubject("ͼƬ");
		message.setSentDate(new Date());
		
		// ���ö๦���ʼ�
		MimeMultipart multipart = new MimeMultipart("related");
		message.setContent(multipart);
		
		// �ʼ����ݣ� html + �ı�
		MimeBodyPart body = new MimeBodyPart();
		// �ʼ�����Ƕ��Դ��
		MimeBodyPart source = new MimeBodyPart();
		
		// ����ʼ����ݣ����๦����;�ʼ�
		multipart.addBodyPart(body);
		multipart.addBodyPart(source);
		
		// ������Դ
		DataSource ds = new FileDataSource(Mail2.class.getResource("1.jpg").getPath());
		DataHandler handler = new DataHandler(ds);
		source.setDataHandler(handler);
		// ������Դid�����ʼ���������
		source.setContentID("1.jpg");
		
		// ��������
		body.setContent("<img src='cid:1.jpg' />�úú�", "text/html;charset=UTF-8");
		
		// �����ʼ�������
		message.saveChanges();
		Transport.send(message);
	}
	
	//4. ����html + img + �����ʼ�
	public static void sendHtmlWithImgAndAttacheEmail() throws Exception {
		// �Ự����
		Session session = Session.getDefaultInstance(prop, new MyAuthenticator(user, pwd));
		// �ʼ�����
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("itcast@it.com"));
		message.setRecipient(RecipientType.TO, new InternetAddress("yuanjie@it.com"));
		message.setSubject("ͼƬ");
		message.setSentDate(new Date());
		
		/*********�������ʼ���***********/
		MimeMultipart mix = new MimeMultipart("mixed");
		message.setContent(mix);
		// ���ҿ�
		MimeBodyPart left = new MimeBodyPart();
		MimeBodyPart right = new MimeBodyPart();
		// ���
		mix.addBodyPart(left);
		mix.addBodyPart(right);
		
		// �����ұߣ� ����(���ģ�1. ·�����룻 2. ͨ��setFileName�����ļ�����Ϊgbk)
		DataSource fileDs = new FileDataSource(URLDecoder.decode(Mail2.class.getResource("�û���֪.doc").getPath(), "UTF-8"));
		DataHandler fileHandler = new DataHandler(fileDs);
		right.setDataHandler(fileHandler);
		// �����ļ�
		right.setFileName(MimeUtility.encodeText("�û���֪.doc"));
		
		
		// ���ö๦���ʼ�
		MimeMultipart multipart = new MimeMultipart("related");
		// ������߿飺 html + �ı� + ��Դ
		left.setContent(multipart);
		
		// �ʼ����ݣ� html + �ı�
		MimeBodyPart body = new MimeBodyPart();
		// �ʼ�����Ƕ��Դ��
		MimeBodyPart source = new MimeBodyPart();
		
		// ����ʼ����ݣ����๦����;�ʼ�
		multipart.addBodyPart(body);
		multipart.addBodyPart(source);
		
		// ������Դ
		DataSource ds = new FileDataSource(Mail2.class.getResource("1.jpg").getPath());
		DataHandler handler = new DataHandler(ds);
		source.setDataHandler(handler);
		// ������Դid�����ʼ���������
		source.setContentID("1.jpg");
		
		// ��������
		body.setContent("<img src='cid:1.jpg' />�úú�", "text/html;charset=UTF-8");
		
		// �����ʼ�������
		message.saveChanges();
		Transport.send(message);
	}
	
	
	// ��֤
	static class MyAuthenticator extends Authenticator{
		private String user;
		private String pwd;
		public MyAuthenticator(String user, String pwd) {
			super();
			this.user = user;
			this.pwd = pwd;
		}
		public MyAuthenticator() {}
		
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, pwd);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
//		sendEmail();
//		sendHtmlEmail();
//		sendHtmlAndImgEmail();
		sendHtmlWithImgAndAttacheEmail();
	}
}

















