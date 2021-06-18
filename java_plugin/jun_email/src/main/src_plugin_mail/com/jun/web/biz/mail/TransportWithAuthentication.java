package com.jun.web.biz.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TransportWithAuthentication {  
	  
    public static void main(String[] args) throws Exception {  
        String smtpServer ="smtp.163.com";  
        String protocol = "smtp";  
        String username = "jsjs9494@163.com";  
        String password = "password.jsjs";  
          
        String from ="jsjs9494@163.com";  
        String to     = "jsjs9494@163.com , 245783660@qq.com";  
        String subject = "javamail 邮件测试";  
        String body = " 邮件内容  ";  
          
        Properties props = new Properties();  
        props.setProperty("mail.transport.protocol", protocol);  
        props.setProperty("mail.host", smtpServer);  
        props.setProperty("mail.smtp.auth", "true");  
        MyAuthentication authentication = new MyAuthentication(username, password);  
        Session session = Session.getInstance(props, authentication);  
        session.setDebug(true);  
          
        //创建代表邮件的MimeMessage对象  
        Message message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(from));  
        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));  
        message.setSentDate(new Date());  
        message.setSubject(subject);  
        message.setText(body);  
        //保存并且生成邮件对象  
        message.saveChanges();  
          
        //建立发送邮件的对象  
        Transport sender = session.getTransport();  
        Transport.send(message);  
          
        //或是使用下面的方法  
/*      sender.connect(); 
        sender.sendMessage(message, message.getRecipients(RecipientType.TO)); 
        sender.close();*/  
    }  
}  