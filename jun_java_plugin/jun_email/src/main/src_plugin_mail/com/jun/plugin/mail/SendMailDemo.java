package com.jun.plugin.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class SendMailDemo {
	//发一封最简单的邮件
	@Test
	public void sendMail() throws Exception{
		//第一步：声明properties对象放信息
		Properties prop = new Properties();
		//设置连接哪一台服务器
		prop.setProperty("mail.host","127.0.0.1");
		//设置是否验证
		prop.setProperty("mail.smtp.auth", "true");
		//第二步：声明用户名和密码
		Authenticator auth = new Authenticator() {
			//此访求返回用户和密码的对象
			public PasswordAuthentication getPasswordAuthentication() {
				PasswordAuthentication pa = 
						new PasswordAuthentication("one", "1234");
				return pa;
			}
		};
		////第二步：获取Session对象
		Session session = 
				Session.getDefaultInstance(prop,auth);
		//设置session的调试模式
		session.setDebug(true);
		//第三步：声明信息
		MimeMessage mm1 = 
				new MimeMessage(session);
		
		//第四步：设置发件人email
		Address from = new InternetAddress("one@wj.com");
		mm1.setFrom(from);
		//第五步：设置收件人
		mm1.setRecipient(RecipientType.TO,new InternetAddress("two@wj.com"));
		//mm1.setRecipient(RecipientType.CC, new InternetAddress("549051701@qq.com"));
		//mm1.setRecipient(RecipientType.BCC, new InternetAddress("wj@itcast.cn"));
		//第六步：设置主题
		mm1.setSubject("这是用Java发One.....的邮件sfasdf3");
		mm1.setContent("你好，这是用java发的邮件,<a href='http://www.baidu.com'>传智</a>", "text/html;charset=UTF-8");
		
		//第七步：
		Transport.send(mm1);
	}
}
