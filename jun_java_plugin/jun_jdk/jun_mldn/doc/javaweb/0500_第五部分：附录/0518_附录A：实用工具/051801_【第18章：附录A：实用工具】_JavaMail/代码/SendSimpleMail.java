package org.lxh.maildemo;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendSimpleMail {

	public static void main(String[] args) throws Exception {
		InternetAddress[] address = null;
		String mailserver = "mldnjava.cn";
		String from = "lxh@mldnjava.cn";
		String to = "mldnqa@sina.com";
		String subject = "北京魔乐科技软件学院";
		String messageText = "www.mldnjava.cn" + "北京魔乐科技软件学院";
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
		msg.setText(messageText); // 发送的内容
		Transport.send(msg, msg.getAllRecipients());
	}

}
