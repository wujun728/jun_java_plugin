package book.email;

import java.io.File;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 发送带附件的邮件
 */
public class AttachmentMailSender {

	public static boolean sendMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件发送的属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getInstance(mailInfo
				.getProperties(), authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO,to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=GBK");
			mainPart.addBodyPart(html);
			// 为邮件添加附件
			String[] attachFileNames = mailInfo.getAttachFileNames();
			if (attachFileNames != null && attachFileNames.length > 0) {
				// 存放邮件附件的MimeBodyPart
				MimeBodyPart attachment = null;
				File file = null;
				for (int i = 0; i < attachFileNames.length; i++) {
					attachment = new MimeBodyPart();
					// 根据附件文件创建文件数据源
					file = new File(attachFileNames[i]);
					FileDataSource fds = new FileDataSource(file);
					attachment.setDataHandler(new DataHandler(fds));
					// 为附件设置文件名
					attachment.setFileName(MimeUtility.encodeWord(file.getName(), "GBK",
							null));
					mainPart.addBodyPart(attachment);
				}
			}
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		// 创建邮件信息
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.sina.com.cn");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("***");
		mailInfo.setPassword("***");
		mailInfo.setFromAddress("***@sina.com");
		mailInfo.setToAddress("***@163.com");
		mailInfo.setSubject("MyMail测试");
		mailInfo.setContent("我的邮件测试\n\rMy test mail\n\r");

		String[] fileNames = new String[3];
		fileNames[0] = "C:/temp/new.txt";
		fileNames[1] = "C:/temp/test.wav";
		fileNames[2] = "C:/temp/mary_photo.jpg";
		mailInfo.setAttachFileNames(fileNames);
		
		AttachmentMailSender.sendMail(mailInfo);
	}
}
