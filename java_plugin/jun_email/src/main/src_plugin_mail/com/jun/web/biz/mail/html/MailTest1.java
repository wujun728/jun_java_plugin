package com.jun.web.biz.mail.html;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;  
  
public class MailTest1 {  
      
      
       public static void send() throws MessagingException, UnsupportedEncodingException {  
             
           String info=ReadHTML.reMailString();  
             
            //邮件服务器  
           String host="smtp.163.com";  
            //发件人  
           String from="jsjs9494@163.com";  
            //收件人  
           String to="jsjs9494@qq.com";  
           //抄送人  
           String toCC1="jsjs9494@163.com";  
           String toCC2="jsjs9494@126.com";  
           String username="jsjs9494@163.com";  
           String password="password.jsjs";  
            //邮件会话属性  
            //Properties  p=System.getProperties();  
           Properties  p=new Properties();  
            p.put("mail.smtp.host", host);  
            /* 
                p.put("mail.smtp.auth", "true"); 
                //创建一个密码验证器 
                Authenticator auth = new MyAuthenticator(username, password); 
                //获得Session 
                Session session=Session.getDefaultInstance(p,auth); 
           */  
            //////////////////sesion获得Transprot方法  
           Session session=Session.getDefaultInstance(p,null);  
                session.setDebug(true);  
              
            /////////////////////  
           //创建Message信息  
           MimeMessage message=new MimeMessage(session);  
           //创建邮件发送者地址  
           Address fromAD = new InternetAddress(from,"系统管理员");  
           //nternetAddress(from)  
           //设置邮件发送者  
                    message.setFrom(fromAD);      
           //创建邮件的接收地址  
           Address toAD = new InternetAddress(to);  
           //创建抄送人数组  
           Address toCAD1=new InternetAddress(toCC1);  
           Address toCAD2=new InternetAddress(toCC2);  
           Address [] toCs={toCAD1,toCAD2};  
           //设置邮件的接收地址  
                    message.setRecipient(Message.RecipientType.TO,toAD);  
                    message.addRecipients(Message.RecipientType.CC,toCs );  
            //设置发送时间  
                    message.setSentDate(new Date());  
            //设置主题    
                    message.setSubject("Hello JavaMail44");   
            /* 
                //设置消息正文,文本          
                        message.setText("Welcome To JavaMail"); 
                //设置HTML内容 
                        message.setContent("<a href='http://www.163.com'>百度</a>","text/html;charset=utf-8"); 
            */    
            // MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象       
            Multipart mainPart = new MimeMultipart();    
            //创建一个包含HTML内容的MimeBodyPart  
            BodyPart body=new MimeBodyPart();  
            //设置html内容  
                body.setContent(info,"text/html;charset=utf-8");  
            //将MimeMultipart设置为邮件内容  
                mainPart.addBodyPart(body);  
                message.setContent(mainPart);  
            ///////////////////////sesion获得Transprot  
            Transport transport=session.getTransport("smtp");  
                transport.connect(host, username, password);  
                transport.sendMessage(message,message.getAllRecipients());  
                transport.close();  
                  
            //////////////////////  
              
            //  Transport.send(message);  
              
             
       }  
       public static void main(String[] args) throws MessagingException, UnsupportedEncodingException  {  
            // TODO Auto-generated method stub  
          send();  
        }  
       
   
          
      
      
      
}  