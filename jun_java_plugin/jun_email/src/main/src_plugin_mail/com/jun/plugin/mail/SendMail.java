package com.jun.plugin.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail extends Thread {


	public void run() {
		try {
			Properties props = new Properties();// key和value的参数，用于发送邮件时使用
			props.setProperty("mail.transport.protocol", "smtp");// 使用的发送协议
			props.setProperty("mail.host", "smtp.163.com");// 发件服务器地址
			props.setProperty("mail.smtp.auth", "true");// 请求认证。如果不认证，有可能不能发送邮件
			Session session = Session.getInstance(props);
			MimeMessage msg = new MimeMessage(session);
			// 设置邮件的头
			msg.setFrom(new InternetAddress("itheimacloud@163.com"));
			// msg.setRecipients(Message.RecipientType.TO, customer.getEmail());
			msg.setRecipients(Message.RecipientType.TO, (Address[]) new Object[2]);
			msg.setSubject("来自训练营的激活邮件");
			// 设置邮件的内容
			msg.setContent(
					"亲爱的小伙伴：<br/>恭喜您注册成为我们的一员，请猛戳<a href='http://localhost:8080/OA/login/LoginServlet?op=active&activeCode=customer.getActiveCode()"
							+ 1 + "'>这里</a>激活您的账户。",
					"text/html;charset=UTF-8");

			msg.saveChanges();
			Transport ts = session.getTransport();
			ts.connect("itheimacloud", "iamsorry");
			ts.sendMessage(msg, msg.getAllRecipients());
			ts.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
