package com.zh.springbootmail.service.impl;

import com.zh.springbootmail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author Wujun
 * @date 2019/6/3
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            javaMailSender.send(message);
            log.info("=============邮件发送成功===============");
        } catch (MailException e) {
            log.error("=============邮件发送失败===============");
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public void sendEmailWithFile(String to, String subject, String text, String filePath) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment(fileName, file);
            javaMailSender.send(mimeMessage);
            log.info("=============邮件发送成功===============");
        } catch (MessagingException e) {
            log.error("=============邮件发送失败===============");
            log.error(e.getMessage(),e);
        }

    }

    @Override
    public void sendWithHtml(String to, String subject, String html) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            // 设置内容，并设置内容 html 格式为 true
            helper.setText(html, true);
            javaMailSender.send(mimeMessage);
            log.info("=============邮件发送成功===============");
        } catch (MessagingException e) {
            log.error("=============邮件发送失败===============");
            log.error(e.getMessage(),e);
        }

    }

    @Override
    public void sendWithImageHtml(String to, String subject, String html, Map<String,String> map) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        FileSystemResource file;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            // 设置 html 中内联的图片
            for (Map.Entry<String,String> e : map.entrySet()){
                file = new FileSystemResource(e.getValue());
                helper.addInline(e.getKey(), file);
            }
            javaMailSender.send(mimeMessage);
            log.info("=============邮件发送成功===============");
        } catch (MessagingException e) {
            log.error("=============邮件发送失败===============");
            log.error(e.getMessage(),e);
        }
    }
}
