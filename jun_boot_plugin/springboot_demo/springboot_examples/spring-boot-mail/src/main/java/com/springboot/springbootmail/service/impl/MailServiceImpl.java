package com.springboot.springbootmail.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.springboot.springbootmail.service.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * MailServiceImpl
 */
@Service
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.fromAddr}")
    private String from;

    @Value("${spring.mail.nickName}")
    private String nickName;

    @Override
    public void sendSimpleEmail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(nickName + "<" + from + ">");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        
        try{
            javaMailSender.send(simpleMailMessage);
            logger.info("简易邮件发送成功");
        } catch(Exception e) {
            logger.error("简易邮件发送异常", e);
        }

    }

    @Override
    public void sendHTMLEmail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(new InternetAddress(from, nickName, "UTF-8"));
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

            javaMailSender.send(message);
            
            logger.info("HTML 模版邮件发送成功");
        } catch (MessagingException e) {
            logger.error("HTML 模版邮件发送失败", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("收件地址编码异常", e);
        }

    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String fileName, String filePath) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(new InternetAddress(from, nickName, "UTF-8"));
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            messageHelper.addAttachment(fileName, file);

            javaMailSender.send(message);
            
            logger.info("带附件邮件发送成功");
        } catch (MessagingException e) {
            logger.error("带附件邮件发送失败", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("收件地址编码异常", e);
        }
    }

    
}