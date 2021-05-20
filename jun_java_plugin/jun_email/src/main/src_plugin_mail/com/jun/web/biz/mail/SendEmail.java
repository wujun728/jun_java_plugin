package com.jun.web.biz.mail;

//文件名 SendEmail.java
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

@WebServlet("/sendmail")
public class SendEmail extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 收件人的电子邮件 ID
		String to = "jsjs9494@163.com";

		// 发件人的电子邮件 ID
		String from = "jsjs9494@163.com";

		// 假设您是从本地主机发送电子邮件
		String host = "smtp.163.com";

		// 获取系统的属性
		Properties properties = System.getProperties();

		// 设置邮件服务器
		properties.setProperty("smtp.163.com", host);

		// 获取默认的 Session 对象
		Session session = Session.getDefaultInstance(properties);

		// 设置响应内容类型
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			// 创建一个默认的 MimeMessage 对象
			MimeMessage message = new MimeMessage(session);
			// 设置 From: header field of the header.
			message.setFrom(new InternetAddress(from));
			// 设置 To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// 设置 Subject: header field
			message.setSubject("This is the Subject Line!");
			// 现在设置实际消息
			message.setText("This is actual message");
			// 发送消息
			Transport.send(message);
			String title = "发送电子邮件";
			String res = "成功发送消息...";
			String docType = "<!DOCTYPE html> \n";
			out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n" + "<body bgcolor=\"#f0f0f0\">\n" + "<h1 align=\"center\">" + title + "</h1>\n" + "<p align=\"center\">"
					+ res + "</p>\n" + "</body></html>");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
	

	  public void sendHtml(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
	      // 收件人的电子邮件 ID
	      String to = "abcd@gmail.com";
	 
	      // 发件人的电子邮件 ID
	      String from = "web@gmail.com";
	 
	      // 假设您是从本地主机发送电子邮件
	      String host = "localhost";
	 
	      // 获取系统的属性
	      Properties properties = System.getProperties();
	 
	      // 设置邮件服务器
	      properties.setProperty("mail.smtp.host", host);
	 
	      // 获取默认的 Session 对象
	      Session session = Session.getDefaultInstance(properties);
	      
		  // 设置响应内容类型
	      response.setContentType("text/html;charset=UTF-8");
	      PrintWriter out = response.getWriter();

	      try{
	         // 创建一个默认的 MimeMessage 对象
	         MimeMessage message = new MimeMessage(session);
	         // 设置 From: header field of the header.
	         message.setFrom(new InternetAddress(from));
	         // 设置 To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));
	         // 设置 Subject: header field
	         message.setSubject("This is the Subject Line!");

	         // 设置实际的 HTML 消息，内容大小不限
	         message.setContent("<h1>This is actual message</h1>",
	                            "text/html" );
	         // 发送消息
	         Transport.send(message);
	         String title = "发送电子邮件";
	         String res = "成功发送消息...";
	         String docType = "<!DOCTYPE html> \n";
	         out.println(docType +
	         "<html>\n" +
	         "<head><title>" + title + "</title></head>\n" +
	         "<body bgcolor=\"#f0f0f0\">\n" +
	         "<h1 align=\"center\">" + title + "</h1>\n" +
	         "<p align=\"center\">" + res + "</p>\n" +
	         "</body></html>");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	   }
	  

	    
	  public void sendMailWithFile(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
	      // 收件人的电子邮件 ID
	      String to = "abcd@gmail.com";
	 
	      // 发件人的电子邮件 ID
	      String from = "web@gmail.com";
	 
	      // 假设您是从本地主机发送电子邮件
	      String host = "localhost";
	 
	      // 获取系统的属性
	      Properties properties = System.getProperties();
	 
	      // 设置邮件服务器
	      properties.setProperty("mail.smtp.host", host);
	 
	      // 获取默认的 Session 对象
	      Session session = Session.getDefaultInstance(properties);
	      
		  // 设置响应内容类型
	      response.setContentType("text/html;charset=UTF-8");
	      PrintWriter out = response.getWriter();

	       try{
	         // 创建一个默认的 MimeMessage 对象
	         MimeMessage message = new MimeMessage(session);
	 
	         // 设置 From: header field of the header.
	         message.setFrom(new InternetAddress(from));
	 
	         // 设置 To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));
	 
	         // 设置 Subject: header field
	         message.setSubject("This is the Subject Line!");
	 
	         // 创建消息部分 
	         BodyPart messageBodyPart = new MimeBodyPart();
	 
	         // 填写消息
	         messageBodyPart.setText("This is message body");
	         
	         // 创建一个多部分消息
	         Multipart multipart = new MimeMultipart();
	 
	         // 设置文本消息部分
	         multipart.addBodyPart(messageBodyPart);
	 
	         // 第二部分是附件
	         messageBodyPart = new MimeBodyPart();
	         String filename = "file.txt";
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);
	 
	         // 发送完整的消息部分
	         message.setContent(multipart );
	 
	         // 发送消息
	         Transport.send(message);
	         String title = "发送电子邮件";
	         String res = "成功发送电子邮件...";
	         String docType = "<!DOCTYPE html> \n";
	         out.println(docType +
	         "<html>\n" +
	         "<head><title>" + title + "</title></head>\n" +
	         "<body bgcolor=\"#f0f0f0\">\n" +
	         "<h1 align=\"center\">" + title + "</h1>\n" +
	         "<p align=\"center\">" + res + "</p>\n" +
	         "</body></html>");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	   }
}