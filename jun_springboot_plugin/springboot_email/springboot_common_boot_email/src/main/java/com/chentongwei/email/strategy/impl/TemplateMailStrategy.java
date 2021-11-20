package com.chentongwei.email.strategy.impl;

import com.chentongwei.email.entity.Mail;
import com.chentongwei.email.strategy.MailStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;

/**
 * @Description: 自定义模板格式发送邮件
 *
 * @author Wujun
 * @Project common-boot-email
 */
@Component
public class TemplateMailStrategy implements MailStrategy {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendMail(String from, Mail mail) {
        final Context ctx = new Context(new Locale(""));
        if (null != mail.getMaps() && mail.getMaps().size() != 0) {
            for (Map.Entry<String, Object> entry : mail.getMaps().entrySet()) {
                ctx.setVariable(entry.getKey(), entry.getValue());
            }
        }
        final String htmlContent = templateEngine.process(mail.getTemplateName(), ctx);
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom(from);
            message.setTo(mail.getTo());
            message.setSubject(mail.getTitle());
            message.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
            LOG.info("模板邮件已经发送{}。", mail.getTo());
        } catch (MessagingException e) {
            e.printStackTrace();
            LOG.error("发送模板邮件【{}】时发生异常！", mail.getTemplateName());
        }
    }
}
