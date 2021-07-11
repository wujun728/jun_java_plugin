package com.ivy.email.vo;

import org.springframework.core.io.ClassPathResource;
import sun.tools.java.ClassPath;

import java.io.File;

/**
 * Created by fangjie04 on 2016/12/1.
 */
public class EmailVO {
    /**
     * 邮件发送者
     */
    private String sender;

    /**
     * 邮件接收者
     */
    private String[] receivers;

    /**
     * 邮件抄送人
     */
    private String[] cc;

    /**
     * 邮件抄送人
     */
    private String[] bcc;

    /**
     * Email发送的内容
     */
    private String emailContent;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件附件
     */
    private File[] attachFile;

    /**
     * 内连内容,内嵌到html之中
     */
    private ClassPathResource[] classPathResource;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public File[] getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(File[] attachFile) {
        this.attachFile = attachFile;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public ClassPathResource[] getClassPathResource() {
        return classPathResource;
    }

    public void setClassPathResource(ClassPathResource[] classPathResource) {
        this.classPathResource = classPathResource;
    }
}
