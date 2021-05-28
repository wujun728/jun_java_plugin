package com.chentongwei.email.strategy.impl;

import com.chentongwei.email.entity.Mail;
import com.chentongwei.email.strategy.MailStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Description: HTML格式发送邮件
 *
 * @author Wujun
 * @Project common-boot-email
 */
@Component
public class HtmlMailStrategy implements MailStrategy {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String from, Mail mail) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getTitle());
            helper.setText(mail.getContent(), true);
            javaMailSender.send(message);
            LOG.info("html邮件已经发送{}。", mail.getTo());
        } catch (MessagingException e) {
            LOG.error("发送html邮件时发生异常！", e);
        }
    }
}
