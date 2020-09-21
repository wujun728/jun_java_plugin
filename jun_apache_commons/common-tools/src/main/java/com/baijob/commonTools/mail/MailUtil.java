package com.baijob.commonTools.mail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtil {
	private static Logger logger = LoggerFactory.getLogger(MailUtil.class);
	
	private static final String SMTP_HOST = "mail.smtp.host";
	private static final String SMTP_PORT = "mail.smtp.port";
	private static final String SMTP_AUTH = "mail.smtp.auth";
	
	/**
	 * 发送邮件给单收件人
	 * @param to 收件人
	 * @param subject 标题
	 * @param content 正文
	 * @param isHtml 是否为HTML
	 */
	public static void sendToSingle(String to, String subject, String content, boolean isHtml) {
		List<String> list = new ArrayList<String>();
		list.add(to);
		send(list, subject, content, isHtml);
	}
	
	/**
	 * 使用默认的设置账户发送邮件
	 * @param tos 收件人列表
	 * @param subject 标题
	 * @param content 正文
	 * @param isHtml 是否为HTML
	 */
	public static void send(Collection<String> tos, String subject, String content, boolean isHtml) {
		MailAccount mailAccount = new MailAccount(MailAccount.MAIL_SETTING_PATH);
		try {
			send(mailAccount, tos, subject, content, isHtml);
		} catch (MessagingException e) {
			logger.error("Send mail error!", e);
		}
	}
	
	/**
	 * 发送邮件给多人
	 * @param mailAccount 邮件认证对象
	 * @param tos 收件人列表
	 * @param subject 标题
	 * @param content 正文
	 * @param isHtml 是否为HTML格式
	 * @throws MessagingException
	 */
	public static void send(final MailAccount mailAccount, Collection<String> tos, String subject, String content, boolean isHtml) throws MessagingException {
		Properties p = new Properties();
		p.put(SMTP_HOST, mailAccount.getHost());
		p.put(SMTP_PORT, mailAccount.getPort());
		p.put(SMTP_AUTH, mailAccount.isAuth());
		
		//认证登录
		Session session = Session.getDefaultInstance(p, 
			mailAccount.isAuth() ? 
				new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mailAccount.getUser(), mailAccount.getPass());
					}
				} : null
		);
		
		Message mailMessage = new MimeMessage(session);
		mailMessage.setFrom(new InternetAddress(mailAccount.getFrom()));
		mailMessage.setSubject(subject);
		mailMessage.setSentDate(new Date());
		
		if(isHtml) {
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(content, "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
		}else {
			mailMessage.setText(content);
		}
		
		for (String to : tos) {
			mailMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
			Transport.send(mailMessage);
			logger.info("Send mail to {} successed.");
		}
	}
}
