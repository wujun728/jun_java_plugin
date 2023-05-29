package com.jun.plugin.base.email.javamail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/** 
 * Java Mail 工具类 
 *  
 * @author Wujun
 * @version 1.0 
 *  
 */  
public class MailTest2 {  
    private static String host;  
    private static String username;  
    private static String password;  
    private static String from;  
    private static String nick;  
  
    static {  
        try {  
            // Test Data  
            host = "smtp.163.com";  
            username = "jsjs9494@163.com";  
            password = "      ";  
            from = "jsjs9494@163.com";  
            nick = "测试admin";  
            // nick + from 组成邮箱的发件人信息  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 发送邮件 
     *  
     * @param to 
     *            收件人列表，以","分割 
     * @param subject 
     *            标题 
     * @param body 
     *            内容 
     * @param filepath 
     *            附件列表,无附件传递null 
     * @return 
     * @throws MessagingException 
     * @throws AddressException 
     * @throws UnsupportedEncodingException 
     */  
    public static boolean sendMail(String to, String subject, String body,  
            List<String> filepath) throws AddressException, MessagingException,  
            UnsupportedEncodingException {  
        // 参数修饰  
        if (body == null) {  
            body = "";  
        }  
        if (subject == null) {  
            subject = "无主题";  
        }  
        // 创建Properties对象  
        Properties props = System.getProperties();  
        // 创建信件服务器  
        props.put("mail.smtp.host", host);  
        props.put("mail.smtp.auth", "true"); // 通过验证  
        // 得到默认的对话对象  
        Session session = Session.getDefaultInstance(props, null);  
        // 创建一个消息，并初始化该消息的各项元素  
        MimeMessage msg = new MimeMessage(session);  
        nick = MimeUtility.encodeText(nick);  
        msg.setFrom(new InternetAddress(nick + "<" + from + ">"));  
        // 创建收件人列表  
        if (to != null && to.trim().length() > 0) {  
            String[] arr = to.split(",");  
            int receiverCount = arr.length;  
            if (receiverCount > 0) {  
                InternetAddress[] address = new InternetAddress[receiverCount];  
                for (int i = 0; i < receiverCount; i++) {  
                    address[i] = new InternetAddress(arr[i]);  
                }  
                msg.addRecipients(Message.RecipientType.TO, address);  
                msg.setSubject(subject);  
                // 后面的BodyPart将加入到此处创建的Multipart中  
                Multipart mp = new MimeMultipart();  
                // 附件操作  
                if (filepath != null && filepath.size() > 0) {  
                    for (String filename : filepath) {  
                        MimeBodyPart mbp = new MimeBodyPart();  
                        // 得到数据源  
                        FileDataSource fds = new FileDataSource(filename);  
                        // 得到附件本身并至入BodyPart  
                        mbp.setDataHandler(new DataHandler(fds));  
                        // 得到文件名同样至入BodyPart  
                        mbp.setFileName(fds.getName());  
                        mp.addBodyPart(mbp);  
                    }  
                    MimeBodyPart mbp = new MimeBodyPart();  
                    mbp.setText(body);  
                    mp.addBodyPart(mbp);  
                    // 移走集合中的所有元素  
                    filepath.clear();  
                    // Multipart加入到信件  
                    msg.setContent(mp);  
                } else {  
                    // 设置邮件正文  
                    msg.setText(body);  
                }  
                // 设置信件头的发送日期  
                msg.setSentDate(new Date());  
                msg.saveChanges();  
                // 发送信件  
                Transport transport = session.getTransport("smtp");  
                transport.connect(host, username, password);  
                transport.sendMessage(msg,  
                        msg.getRecipients(Message.RecipientType.TO));  
                transport.close();  
                return true;  
            } else {  
                System.out.println("None receiver!");  
                return false;  
            }  
        } else {  
            System.out.println("None receiver!");  
            return false;  
        }  
    }  
  
    public static void main(String[] args) throws AddressException,  
            UnsupportedEncodingException, MessagingException {  
        List<String> filepath = new ArrayList<>();  
        filepath.add("E:\\books.xml");  
        filepath.add("E:\\books.xml");  
        sendMail("245783660@qq.com,wujun728@163.com", "注册信息邮件", "注册邮件，有附件",  filepath);  
    }  
}