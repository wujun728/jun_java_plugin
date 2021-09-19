package com.jun.plugin.itcast.javamail2;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class Demo3 {

	/**
	 * @param args add by zxx ,Feb 5, 2009
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Session session = Session.getInstance(new Properties());
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("\"" + MimeUtility.encodeText("���ǲ���") + "\" <itcast_test@sina.com>"));
		msg.setSubject("���ǵ�Java��ѵ�������ţ����");		
		msg.setReplyTo(new Address[]{new InternetAddress("lili@126.com")});
		msg.setRecipients(RecipientType.TO,InternetAddress.parse(MimeUtility.encodeText("�����") + " <llm@itcast.cn>," + MimeUtility.encodeText("��Т��") + " <zxx@itcast.cn>"));
		MimeMultipart msgMultipart = new MimeMultipart("mixed");
		msg.setContent(msgMultipart);

		MimeBodyPart attch1 = new MimeBodyPart();		
		MimeBodyPart attch2 = new MimeBodyPart();		
		MimeBodyPart content = new MimeBodyPart();
		msgMultipart.addBodyPart(attch1);		
		msgMultipart.addBodyPart(attch2);		
		msgMultipart.addBodyPart(content);

		DataSource ds1 = new FileDataSource(
				"resource\\Java��ѵ.txt"	
			);
		DataHandler dh1 = new DataHandler(ds1 );
		attch1.setDataHandler(dh1);
		attch1.setFileName(
				MimeUtility.encodeText("java��ѵ.txt")
				);
		
		DataSource ds2 = new FileDataSource(
				"resource\\slogo.gif"		
			);
		DataHandler dh2 = new DataHandler(ds2 );
		attch2.setDataHandler(dh2);		
		attch2.setFileName("slogo.gif");
		
		MimeMultipart bodyMultipart = new MimeMultipart("related");
		content.setContent(bodyMultipart);
		MimeBodyPart htmlPart = new MimeBodyPart();		
		MimeBodyPart gifPart = new MimeBodyPart();		
		bodyMultipart.addBodyPart(htmlPart);
		bodyMultipart.addBodyPart(gifPart);		

		DataSource gifds = new FileDataSource(
				"resource\\logo.gif"	
			);
		DataHandler gifdh = new DataHandler(gifds);		
		gifPart.setDataHandler(gifdh);
		gifPart.setHeader("Content-Location", "http://www.itcast.cn/logo.gif");
		
		htmlPart.setContent("���ǵ�Java��ѵ�������ţ���𣿴�Ҷ���ô˵,��������Ǳ���һ�£���������Լ��ó�����ɺͷ��͵��ʼ�Ŷ��<img src='http://www.itcast.cn/logo.gif'>"
					, "text/html;charset=gbk");
		
		msg.saveChanges();
		
		OutputStream ips = new FileOutputStream("resource\\demo3.eml");
		msg.writeTo(ips);
		ips.close();
		
		
	}

}
