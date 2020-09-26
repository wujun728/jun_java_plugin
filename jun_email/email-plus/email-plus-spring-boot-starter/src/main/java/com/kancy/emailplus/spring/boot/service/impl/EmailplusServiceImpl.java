package com.kancy.emailplus.spring.boot.service.impl;

import com.kancy.emailplus.core.exception.EmailException;
import com.kancy.emailplus.spring.boot.listener.event.SendEmailEvent;
import com.kancy.emailplus.spring.boot.message.EmailMessage;
import com.kancy.emailplus.spring.boot.service.EmailplusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.Map;

/**
 * EmailplusServiceImpl
 *
 * @author kancy
 * @date 2020/2/20 23:27
 */
public class EmailplusServiceImpl implements EmailplusService {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 发送邮件
     *
     * @param emailKey
     * @throws EmailException
     */
    @Override
    public void sendSimpleEmail(String emailKey) {
        send(EmailMessage.create(emailKey));
    }

    /**
     * 发送邮件
     *
     * @param emailKey
     * @param content
     * @throws EmailException
     */
    @Override
    public void sendSimpleEmail(String emailKey, String content) {
        EmailMessage emailMessage = EmailMessage.create(emailKey);
        emailMessage.setContent(content);
        send(emailMessage);
    }

    /**
     * 发送邮件
     *
     * @param emailKey
     * @param content
     * @param files
     * @throws EmailException
     */
    @Override
    public void sendFileEmail(String emailKey, String content, Map<String, File> files) {
        EmailMessage emailMessage = EmailMessage.create(emailKey);
        emailMessage.setContent(content);
        emailMessage.setFiles(files);
        send(emailMessage);
    }

    /**
     * 发送邮件
     *
     * @param emailKey
     * @throws EmailException
     */
    @Override
    public void sendTemplateEmail(String emailKey) {
        sendSimpleEmail(emailKey);
    }

    /**
     * 发送邮件
     *
     * @param emailKey
     * @param templateData
     * @throws EmailException
     */
    @Override
    public void sendTemplateEmail(String emailKey, Map<String, Object> templateData) {
        EmailMessage emailMessage = EmailMessage.create(emailKey);
        emailMessage.setTemplateData(templateData);
        send(emailMessage);
    }

    /**
     * 发送邮件
     *
     * @param message
     * @throws EmailException
     */
    private void send(EmailMessage message) {
        applicationContext.publishEvent(new SendEmailEvent(this, message));
    }
}
