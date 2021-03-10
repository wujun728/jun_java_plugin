package junit;

import org.junit.Test;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by CDHong on 2018/4/8.
 */
public class JavaMail {

    public static String myEmailAccount = "xxxxx@qq.com"; //发件帐号
    public static String myEmailPassword = "xxxx"; //授权码

    // SMTP 服务器地址
    public static String myEmailSMTPHost = "smtp.qq.com";

    // 收件人邮箱
    public static String receiveMailAccount = "xxxx@qq.com";

    @Test
    public void sendMail() throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        //开启 SSL 安全连接
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount);
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        // 6. 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        // 7. 关闭连接
        transport.close();
    }

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        //邮件实例
        MimeMessage message = new MimeMessage(session);
        //发件人
        message.setFrom(new InternetAddress(sendMail,"邙星魂","UTF-8"));
        //收件人
        message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(receiveMail,"UTF-8"));
        //邮件主题
        message.setSubject("邙星魂JAVA开发博客验证","UTF-8");
        //邮件主题
        message.setContent("这是测试信息，邮件主题....<a href='https://blog.csdn.net/u010158540/article/details/79833011'>邙星魂JAVA开发博客</a>","text/html;charset=UTF-8");
        //发件时间
        message.setSentDate(new Date());
        //保存设置
        message.saveChanges();
        return message;
    }
}
