package com.jun.plugin.mail;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
/**发送邮件*/
public class SendMail2 {
	public SendMail2(String updatetimeinfo,String userName,String userKey,String destMailAdress) {
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "false");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.163.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");
		try {
			// 设置环境信息
			Session session = Session.getInstance(props);
			// 创建邮件对象
			Message msg = new MimeMessage(session);
			msg.setSubject("Hosts Update Notify"+updatetimeinfo);
			// 设置邮件内容
			msg.setText("Update Successfully\nVersion：\n"+updatetimeinfo+"\n");
			// 设置发件人
			msg.setFrom(new InternetAddress(userName+"@163.com"));
			Transport transport = session.getTransport();
			// 连接邮件服务器
			transport.connect(userName, userKey);
			// 发送邮件
			transport.sendMessage(msg, new Address[] { new InternetAddress(
					destMailAdress) });
			// 关闭连接
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
