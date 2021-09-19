package com.gct.bas.common.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * JavaMail发送邮件:前提是QQ邮箱里帐号设置要开启POP3/SMTP协议
 */
public class SendMailUseSSL {

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param host
     * @param user
     * @param from
     * @param SSL
     * @param protocol
     * @param to
     * @return
     * @throws Exception
     * @author Wang Xuantao
     */
  public  static String sendMail(String host,String user ,String from ,String SSL ,String protocol,String to) throws Exception{

        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", protocol);

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
        ts.connect(host, user, SSL);// 后面的字符是授权码，用qq密码反正我是失败了（用自己的，别用我的，这个号是我瞎编的，为了。。。。）
        // 创建邮件
        Message message = createSimpleMail(session,from,to);
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
        return "ok";
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(Session session,String from, String to) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(from));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // 邮件的标题
        message.setSubject("JavaMail测试");
        // 邮件的文本内容
        message.setContent("JavaMail发送邮件成功！LALALALALALA", "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }
}