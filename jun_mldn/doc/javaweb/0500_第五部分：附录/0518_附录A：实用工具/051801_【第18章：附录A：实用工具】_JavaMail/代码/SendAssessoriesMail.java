package org.lxh.maildemo;

import java.io.File;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendAssessoriesMail {

	public static void main(String[] args) throws Exception {
		InternetAddress[] address = null;
		String mailserver = "mldnjava.cn";
		String from = "lxh@mldnjava.cn";
		String to = "mldnqa@sina.com";
		String subject = "北京魔乐科技软件学院";
		String messageText = "<h1>"
				+ "<a href=\"www.mldnjava.cn\">www.mldnjava.cn</a>"
				+ "北京魔乐科技软件学院" + "</h1>";
		java.util.Properties props = System.getProperties();
		props.put("mail.smtp.host", mailserver);
		props.put("mail.smtp.auth", "true");
		MySecurity msec = new MySecurity("lxh", "mldnjava");
		Session mailSession = Session.getDefaultInstance(props, msec);
		mailSession.setDebug(false);
		Message msg = new MimeMessage(mailSession);// 创建文件信息
		msg.setFrom(new InternetAddress(from)); // 设置传送邮件的发信人
		address = InternetAddress.parse(to, false); // 指定收信人的信箱
		msg.setRecipients(Message.RecipientType.TO, address); // 向指定邮箱发送
		msg.setSubject(subject);
		msg.setSentDate(new Date()); // 立刻发送

		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart(); // 添加附件
		DataSource source = new FileDataSource("d:" + File.separator
				+ "photo.gif");
		messageBodyPart.setDataHandler(new DataHandler(source)) ;
		messageBodyPart.setFileName("mldn.gif") ;
		multipart.addBodyPart(messageBodyPart) ;
		
		messageBodyPart = new MimeBodyPart() ;
		messageBodyPart.setContent(messageText,"text/html;charset=GBK") ;
		multipart.addBodyPart(messageBodyPart) ;
		msg.setContent(multipart) ;
		Transport.send(msg, msg.getAllRecipients());
	}

}
