package com.kancy.emailplus.core;

import com.kancy.emailplus.core.exception.EmailException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * AbstractEmailSender
 *
 * @author kancy
 * @date 2020/2/22 15:21
 */
public abstract class AbstractEmailSender implements EmailSender {

    private Session session;
    private Properties javaMailProperties = new Properties();

    private String encoding = StandardCharsets.UTF_8.name();

    /**
     * 创建MimeMessage
     * @param message
     * @return
     * @throws MessagingException
     */
    protected MimeMessage createMimeMessage(Email message) {
        try {
            // 创建MimeMessage
            MimeMessage mimeMessage = new MimeMessage(getSession());
            // 设置发送者
            mimeMessage.setFrom(new InternetAddress(getFromEmailAddress()));
            // 设置发送方式与接收者
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, toInternetAddress(message.getTo()));
            if (!isEmpty(message.getCc())){
                mimeMessage.setRecipients(MimeMessage.RecipientType.CC, toInternetAddress(message.getCc()));
            }
            // 设置主题
            mimeMessage.setSubject(message.getSubject());
            // 设置内容
            if (message.isMultipart()){
                // MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
                Multipart multiPart = new MimeMultipart();
                BodyPart bodyPart = new MimeBodyPart();
                // 设置html邮件消息内容
                bodyPart.setContent(message.getContent(), createContentType(message.isHtml()));
                multiPart.addBodyPart(bodyPart);

                //添加附件
                Map<String, File> files = message.getFiles();
                Set<Map.Entry<String, File>> entries = files.entrySet();
                for (Map.Entry<String, File> fileEntry : entries) {
                    try {
                        MimeBodyPart fileBodyPart = new MimeBodyPart();
                        FileDataSource fds = new FileDataSource(fileEntry.getValue());
                        fileBodyPart.setDataHandler(new DataHandler(fds));
                        fileBodyPart.setFileName(MimeUtility.encodeText(fileEntry.getKey()));
                        multiPart.addBodyPart(fileBodyPart);
                    } catch (UnsupportedEncodingException ex) {
                        throw new EmailException("Unsupported encoding exception", ex);
                    }
                }
                mimeMessage.setContent(multiPart);
            } else {
                mimeMessage.setContent(message.getContent(), createContentType(message.isHtml()));
            }
            // 设置发送时间
            Date sentDate = mimeMessage.getSentDate();
            if (Objects.isNull(sentDate)) {
                sentDate = new Date();
                mimeMessage.setSentDate(sentDate);
            }
            message.setSentDate(sentDate);
            return mimeMessage;
        } catch (MessagingException ex) {
            throw new EmailException("Create mimeMessage fail", ex);
        }
    }

    /**
     * 发件人邮箱地址
     * @return
     */
    protected abstract String getFromEmailAddress();

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Return the JavaMail {@code Session},
     * lazily initializing it if hasn't been specified explicitly.
     */
    protected synchronized Session getSession() {
        if (this.session == null) {
            this.session = Session.getInstance(this.javaMailProperties);
        }
        return this.session;
    }

    /**
     * Set the JavaMail {@code Session}, possibly pulled from JNDI.
     */
    protected synchronized void setSession(Session session) {
        assert session != null : "Session must not be null";
        this.session = session;
    }

    /**
     * Set JavaMail properties for the {@code Session}.
     */
    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
        synchronized (this) {
            this.session = null;
        }
    }

    public Properties getJavaMailProperties() {
        return javaMailProperties;
    }

    private String createContentType(boolean isHtml) {
        return String.format("text/%s;charset=%s", isHtml ? "html" : "plain", getEncoding());
    }

    private InternetAddress[] toInternetAddress(String[] stringAddresses) throws AddressException {
        InternetAddress[] addresses = new InternetAddress[stringAddresses.length];
        for (int i = 0; i < stringAddresses.length; i++) {
            addresses[i] = new InternetAddress(stringAddresses[i]);
        }
        return addresses;
    }

    private boolean isEmpty(Object object) {
        if (Objects.isNull(object)){
            return true;
        }
        if (object instanceof String){
            return object.toString().isEmpty();
        } else if(object.getClass().isArray()){
            return Objects.equals(Array.getLength(object), 0);
        }
        throw new IllegalArgumentException();
    }

}
