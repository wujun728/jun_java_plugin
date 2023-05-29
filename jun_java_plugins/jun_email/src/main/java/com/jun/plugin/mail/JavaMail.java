package com.jun.plugin.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class JavaMail {
	public static void main(String[] args) {
		// JavaMail.send(" aaaaaaaaaaaaaaa ");
		// JavaMail.send("title "," bbbbbbbbbbbbb ");
		// JavaMail.sendHtml(" ccccccccccccccccc ");
		// JavaMail.sendMail("jsjs9494@163.com", "abcd");
	}

	static final String fromMail = "jsjs9494@163.com";
	static final String user = "jsjs9494@163.com";
	static final String password = "password.jsjs";
	static final String toMail = "jsjs9494@163.com";
	static final String toHtmlMail = "jsjs9494@163.com";
	static final String mailTitle = "[ALERTS]-System Tips";
	static final String htmlMailTitle = "[REPORT] - Operator Data";

	public static void send(String title, String mailContent) {
		sendMail(fromMail, user, password, toMail, title, mailContent);
	}

	public static void send(String mailContent) {
		sendMail(fromMail, user, password, toMail, mailTitle, mailContent);
	}

	public static void sendHtml(String mailContent) {
		if (mailContent == null || mailContent.trim().isEmpty() || mailContent.length() < 20) {
			return;
		}
		sendHtmlMail(fromMail, user, password, toHtmlMail, htmlMailTitle, mailContent);
	}

	/**
	 * @param fromMail
	 * @param user
	 * @param password
	 * @param toMail
	 * @param mailTitle
	 * @param mailContent
	 */
	private static void sendMail(String fromMail, String user, String password, String toMail, String mailTitle,
			String mailContent) {
		try {
			// String hostIP = ServerInfoCache.instance().getHostIP();
			// String hostName = ServerInfoCache.instance().getHostName();
			// String SERVER_INFO = String.format("SERVER IP -> [%s] - SERVER NAME ->
			// [%s]\r\n<System email - please do not reply> The following is the email body
			// :\r\n========================================================", hostIP,
			// hostName);

			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.163.com");
			props.put("mail.smtp.port", 465);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);

			Session session = Session.getInstance(props);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromMail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
			message.setSubject(mailTitle);
			message.setText("\r\n\r\n\r\n" + mailContent);
			message.setSentDate(new Date());
			message.saveChanges();
			Transport transport = session.getTransport();
			transport.connect(user, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			// LogUtil.error(e);
		}
	}

	private static void sendHtmlMail(String fromMail, String user, String password, String toMail, String mailTitle,
			String mailContent) {
		try {
			// String hostIP = ServerInfoCache.instance().getHostIP();
			// String hostName = ServerInfoCache.instance().getHostName();
			// String SERVER_INFO = String.format("SERVER IP -> [%s] - SERVER NAME -> [%s]
			// %s ", hostIP, hostName);

			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);

			Properties props = new Properties();
			props.put("mail.smtp.host", "mail.163.com");
			props.put("mail.smtp.port", 465);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);

			Session session = Session.getInstance(props);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromMail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
			message.setSubject(mailTitle);
			message.setContent(mailContent, "text/html");
			message.setSentDate(new Date());
			message.saveChanges();
			Transport transport = session.getTransport();
			transport.connect(user, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			// LogUtil.error(e);
		}
	}

	public static void sendMail(String email, String emailMsg) throws AddressException, MessagingException {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.host", "smtp.163.com");
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("jsjs9494@163.com", "password.jsjs");
			}
		};
		Session session = Session.getInstance(props, auth);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("jsjs9494@163.com"));
		message.setRecipient(RecipientType.TO, new InternetAddress(email));
		message.setSubject("用户注册确认邮件");
		// message.setText("这是封激活邮件，<a href='#'>点击</a>");
		message.setContent(emailMsg, "text/html;charset=utf-8");
		Transport.send(message);
	}

}
