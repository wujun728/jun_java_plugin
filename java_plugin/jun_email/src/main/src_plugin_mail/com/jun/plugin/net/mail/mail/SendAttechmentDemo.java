package com.jun.plugin.net.mail.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.junit.Test;

public class SendAttechmentDemo {
	@Test
	public void sendFile() throws Exception{
		Properties p = new Properties();
		p.setProperty("mail.host","smtp.163.com");
		p.setProperty("mail.smtp.auth","true");
		Session s = Session.getDefaultInstance(p,new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("wj_leaf12345", "1qaz2wsx");
			}
		});
		s.setDebug(true);
		
		
		//声明MimeMessage
		MimeMessage msg = new MimeMessage(s);
		msg.setFrom(new InternetAddress("wj_leaf12345@163.com"));
		msg.setRecipient(RecipientType.TO, new InternetAddress("wj_leaf12345@126.com"));
		msg.setSubject("图片的");
		
		//第一步：声明多处理的Part
		MimeMultipart mm = new MimeMultipart();
		
		//第二步：声明
		MimeBodyPart body1 = new MimeBodyPart();
		//第三步：设置符件
		DataSource ds = new FileDataSource(new File("./img/a.jpg"));
		DataHandler dh = new DataHandler(ds);
		body1.setDataHandler(dh);
		//必须要设置名称
		body1.setFileName(MimeUtility.encodeText("美女.jpg"));
		
		
		MimeBodyPart body2 = new MimeBodyPart();
		//第三步：设置符件
		DataSource ds2 = new FileDataSource(new File("./img/b.jpg"));
		DataHandler dh2 = new DataHandler(ds2);
		body2.setDataHandler(dh2);
		//必须要设置名称
		body2.setFileName(MimeUtility.encodeText("美女2.jpg"));
		
		
		MimeBodyPart body3 = new MimeBodyPart();
		//第三步：设置符件
		DataSource ds3 = new FileDataSource(new File("./img/m.mp3"));
		DataHandler dh3 = new DataHandler(ds3);
		body3.setDataHandler(dh3);
		//必须要设置名称
		body3.setFileName(MimeUtility.encodeText("世纪末.mp3"));
		
		//将body1添加到mm
		mm.addBodyPart(body1);
		mm.addBodyPart(body2);
		mm.addBodyPart(body3);
		
		msg.setContent(mm);
		
		//发送
		Transport.send(msg);
		
	}
}
