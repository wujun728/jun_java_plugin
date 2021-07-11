package com.ivy.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fangjie04 on 2016/12/1.
 */
@Component
public class MailConfig {

    // ---------------- 邮件配置 ----------------------------
    /**
     * 邮件接收人
     */
    @Value("#{'${mail.receivers}'.split(',')}")
    private List<String> mailReceivers;

    /**
     * 邮件抄送
     */
    @Value("#{'${mail.cc}'.split(',')}")
    private List<String> mailCc;

    /**
     *  邮件密送
     */
    @Value("#{'${mail.bcc}'.split(',')}")
    private List<String> mailBcc;

    /**
     * 邮件发送者
     */
    @Value("${mail.sender}")
    private String mailSender;

    /**
     * 邮件服务器
     */
    @Value("${mail.host}")
    private String mailHost;

    /**
     *  邮箱用户名
     */
    @Value("${mail.username}")
    private String mailUserName;

    /**
     * 邮箱密码
     */
    @Value("${mail.password}")
    private String mailPassWd;

    /**
     *  端口号
     */
    @Value("${mail.port}")
    private int mailPort;


    public List<String> getMailReceivers() {
        return mailReceivers;
    }

    public void setMailReceivers(List<String> mailReceivers) {
        this.mailReceivers = mailReceivers;
    }

    public List<String> getMailCc() {
        return mailCc;
    }

    public void setMailCc(List<String> mailCc) {
        this.mailCc = mailCc;
    }

    public List<String> getMailBcc() {
        return mailBcc;
    }

    public void setMailBcc(List<String> mailBcc) {
        this.mailBcc = mailBcc;
    }

    public String getMailSender() {
        return mailSender;
    }

    public void setMailSender(String mailSender) {
        this.mailSender = mailSender;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getMailUserName() {
        return mailUserName;
    }

    public void setMailUserName(String mailUserName) {
        this.mailUserName = mailUserName;
    }

    public String getMailPassWd() {
        return mailPassWd;
    }

    public void setMailPassWd(String mailPassWd) {
        this.mailPassWd = mailPassWd;
    }

    public int getMailPort() {
        return mailPort;
    }

    public void setMailPort(int mailPort) {
        this.mailPort = mailPort;
    }
}
