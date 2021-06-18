package com.chentongwei.common.email.biz;

import com.chentongwei.common.email.entity.Mail;
import com.chentongwei.common.email.enums.status.MailContentTypeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 邮件发送类
 **/
@Component
public class MailBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private JavaMailSender sender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    /** 邮件实体 */
    private static Mail mail = new Mail();

    /**
     * 设置邮件标题
     *
     * @param title：邮件标题
     * @return
     */
    public MailBiz title(String title) {
        mail.setTitle(title);
        return this;
    }

    /**
     * 设置邮件内容
     *
     * @param content：内容
     * @return
     */
    public MailBiz content(String content) {
        mail.setContent(content);
        return this;
    }

    /**
     * 设置邮件格式
     *
     * @param typeEnum：邮件格式
     * @return
     */
    public MailBiz contentType(MailContentTypeEnum typeEnum) {
        mail.setContentType(typeEnum.value());
        return this;
    }

    /**
     * 设置请求目标邮件地址
     *
     * @param to：请求目标邮件地址
     * @return
     */
    public MailBiz to(String to) {
        mail.setTo(to);
        return this;
    }

    /**
     * 执行发送邮件
     *
     * @param template：模板名称
     * @param maps：参数
     */
    public void send(String template, Map<String, Object> maps) {
        //文本发送
        if(Objects.equals(mail.getContentType(), MailContentTypeEnum.TEXT.value())) {
            sendSimpleMail();
        }
        //html发送
        if(Objects.equals(mail.getContentType(), MailContentTypeEnum.HTML.value())) {
            sendHtmlMail();
        }
        //模板发送
        if(Objects.equals(mail.getContentType(), MailContentTypeEnum.TEMPLATE.value())) {
            sendTemplateMail(template, maps);
        }
    }

    /**
     * 发送纯文本的简单邮件
     */
    private void sendSimpleMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mail.getTo());
        message.setSubject(mail.getTitle());
        message.setText(mail.getContent());
        try {
            sender.send(message);
            LOG.info("纯文本的邮件已经发送给【{}】。", mail.getTo());
        } catch (Exception e) {
            LOG.error("纯文本邮件发送时发生异常！", e);
        }
    }

    /**
     * 发送html格式的邮件
     */
    private void sendHtmlMail(){
        MimeMessage message = sender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getTitle());
            helper.setText(mail.getContent(), true);
            sender.send(message);
            LOG.info("html邮件已经发送{}。", mail.getTo());
        } catch (MessagingException e) {
            LOG.error("发送html邮件时发生异常！", e);
        }
    }

    /**
     * 发送模板邮件
     *
     * @param templateName:模板名称
     * @param maps：参数
     */
    private void sendTemplateMail(String templateName, Map<String, Object> maps) {
        final Context ctx = new Context(new Locale(""));
        for (Map.Entry<String, Object> entry : maps.entrySet()) {
            ctx.setVariable(entry.getKey(), entry.getValue());
        }
        final String htmlContent = templateEngine.process(templateName, ctx);
        final MimeMessage mimeMessage = sender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom(from);
            message.setTo(mail.getTo());
            message.setSubject(mail.getTitle());
            message.setText(htmlContent, true);
            LOG.info("模板邮件已经发送{}。", mail.getTo());
        } catch (MessagingException e) {
            e.printStackTrace();
            LOG.error("发送模板邮件【{}】时发生异常！", templateName);
        }
        sender.send(mimeMessage);
    }
}
