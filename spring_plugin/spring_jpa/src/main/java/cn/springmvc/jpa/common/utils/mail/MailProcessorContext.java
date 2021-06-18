package cn.springmvc.jpa.common.utils.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import cn.springmvc.jpa.common.constants.Constants;
import cn.springmvc.jpa.common.exception.BusinessException;
import cn.springmvc.jpa.common.utils.PropertyConfigurer;

/**
 * 发送邮件工具类
 * 
 * @author Vincent.wang
 * 
 */
public class MailProcessorContext {

    private final Logger log = LoggerFactory.getLogger(MailProcessorContext.class);

    /** 发件人邮箱地址 **/
    private String sendMailAddres;

    /** 发件人邮箱密码 **/
    private String sendMailPassword;

    /** Mail smtp **/
    private String smtp;

    /*** 邮件主题 **/
    private String subject;

    /** 邮件内容 **/
    private StringBuilder body;

    /** 收件人列表 **/
    private String toMails[];

    /** cc邮件列表 **/
    private String cc[];

    /** bcc邮件列表 **/
    private String bcc[];

    /** send mail smtp port **/
    private int smtpPort = 25;

    /** 附件流 **/
    private List<MailFile> files;

    /**
     * 设置SMTP服务器用户认证
     * 
     * @param sendMailAddres
     *            用户名
     * @param sendMailPassword
     *            密码
     */
    public void setSmtpAuthentication(String sendMailAddres, String sendMailPassword) {
        this.sendMailAddres = sendMailAddres;
        this.sendMailPassword = sendMailPassword;
    }

    /**
     * 设置SMTP
     * 
     * @param smtp
     *            设置SMTP
     */
    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    /**
     * 设置邮件主题
     * 
     * @param subject
     *            邮件主题
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 设置邮件文字内容
     * 
     * @param body
     *            邮件文字内容
     */
    public void setEmailBody(StringBuilder body) {
        this.body = body;
    }

    /**
     * 设置收件人地址
     * 
     * @param toMails
     *            收件人Email地址
     */
    public void setToMails(String[] toMails) {
        this.toMails = toMails;
    }

    /**
     * 设置抄送地址
     * 
     * @param cc
     *            抄送地址
     */
    public void setCc(String[] cc) {
        this.cc = cc;
    }

    /**
     * 设置暗送地址
     * 
     * @param bcc
     *            暗送地址
     */
    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    /**
     * 设置发送邮件端口
     * 
     * @param smtpPort
     *            设置发送邮件端口,默认25
     */
    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    /**
     * 添加多个附件
     * 
     * @param files
     *            附件对象集合
     */
    public void setMailFile(List<MailFile> files) {
        this.files = files;
    }

    /**
     * 添加一个附件
     * 
     * @param file
     *            附件对象
     */
    public void setFile(MailFile file) {
        if (this.files == null) {
            this.files = new ArrayList<MailFile>();
        }
        this.files.add(file);
    }

    /**
     * 添加一个附件
     * 
     * @param fileName
     *            附件名
     * @param file
     *            附件
     */
    public void setFile(String fileName, byte[] file) {
        if (this.files == null) {
            this.files = new ArrayList<MailFile>();
        }
        MailFile ma = new MailFile();
        ma.setFile(fileName, file);
        this.files.add(ma);
    }

    /**
     * 发送邮件
     */
    public void toSend() throws BusinessException {
        if (StringUtils.isEmpty(sendMailAddres) || StringUtils.isEmpty(sendMailPassword)) {
            String user = PropertyConfigurer.getValue(Constants.MAIL_USER);
            String passWord = PropertyConfigurer.getValue(Constants.MAIL_PASSWORD);
            setSmtpAuthentication(user, passWord);
        }
        if (StringUtils.isEmpty(smtp)) {
            String smtp = PropertyConfigurer.getValue(Constants.MAIL_SMTP);
            setSmtp(smtp);
        }

        try {
            JavaMailSenderImpl mail = new JavaMailSenderImpl();
            mail.setUsername(sendMailAddres);
            mail.setPassword(sendMailPassword);
            mail.setPort(smtpPort);
            // 设置发件人及密码
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.host", smtp);// 存储发送邮件服务器的信息
            prop.setProperty("mail.smtp.auth", "true");

            mail.setJavaMailProperties(prop);
            MimeMessage message = mail.createMimeMessage();
            MimeMessageHelper messageHelp = new MimeMessageHelper(message, true, "UTF-8");
            // 发件人
            if (sendMailAddres.isEmpty())
                throw new BusinessException("Please set userMail address.");
            messageHelp.setFrom(sendMailAddres);

            // 收件人
            if (toMails == null || toMails.length == 0)
                throw new BusinessException("Please set toMails address.");
            messageHelp.setTo(toMails);

            // CC
            if (cc != null && cc.length > 0)
                messageHelp.setCc(cc);

            // bcc
            if (bcc != null && bcc.length > 0)
                messageHelp.setBcc(bcc);

            if (StringUtils.isEmpty(subject))
                throw new BusinessException("Please set Mail Subject.");
            // 邮件主题
            messageHelp.setSubject(subject);

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<html><head>");
            emailBody.append("<META http-equiv=Content-Type content='text/html; charset=UTF-8'>");
            emailBody.append("<meta http-equiv='Content-Language' content='zh-cn' />");
            emailBody.append("<meta http-equiv='Pragma' content='no-cache' />");
            emailBody.append("<meta http-equiv='Expires' content='-1' />");
            emailBody.append("<meta http-equiv='Cache-Control' content='no-cache' />");
            emailBody.append("</head>");
            emailBody.append("<body>");
            // 邮件内容
            emailBody.append(body.toString());
            emailBody.append("</body></html>");

            messageHelp.setText(emailBody.toString(), true);

            // 邮件附件
            if (files != null && files.size() > 0) {
                for (MailFile ma : files) {
                    messageHelp.addAttachment(MimeUtility.encodeText(ma.getAccessoryName()), ma.getInputStreams());
                }
            }
            mail.send(message);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            log.error("## Send Mail failure! Error log is " + e1.getMessage());
            throw new BusinessException("EXCEL_UTIL_00011", e1);
        } catch (MessagingException e1) {
            e1.printStackTrace();
            log.error("## Send Mail Mail failure! Error log is " + e1.getMessage());
            throw new BusinessException("EXCEL_UTIL_00011", e1);
        }

    }

}

/**
 * 附件包装类，用于发送邮件
 * 
 * @author Vincent.wang
 * 
 */
class MailFile {

    private String fileName;

    private InputStreamSource stream;

    /**
     * 添加一个附件
     * 
     * @param fileName
     *            附件名
     * @param file
     *            附件
     */
    public void setFile(String fileName, InputStreamSource file) {
        if (StringUtils.isEmpty(fileName))
            throw new NullPointerException("Please set file.");
        this.fileName = fileName;
        if (file == null)
            throw new NullPointerException("Please set file.");
        this.stream = file;
    }

    /**
     * 添加一个附件
     * 
     * @param fileName
     *            附件名
     * @param file
     *            附件
     */
    public void setFile(String fileName, byte[] file) {
        if (StringUtils.isEmpty(fileName))
            throw new NullPointerException("Please set file.");
        this.fileName = fileName;
        if (file == null)
            throw new NullPointerException("Please set file.");
        this.stream = new ByteArrayResource(file);
    }

    public InputStreamSource getInputStreams() {
        return stream;
    }

    public String getAccessoryName() {
        return fileName;
    }

}
