package com.itmuch.email;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wujun
 */
@SuppressWarnings("ALL")
@RestController
public class MailController {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private Configuration freemarkerConfiguration;

    /**
     * 简单邮件测试
     *
     * @return success
     */
    @GetMapping("/simple")
    public String simple() {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发件人邮箱
        message.setFrom(this.mailProperties.getUsername());
        // 收信人邮箱
        message.setTo("511932633@qq.com");
        // 邮件主题
        message.setSubject("简单邮件测试");
        // 邮件内容
        message.setText("简单邮件测试");
        this.javaMailSender.send(message);
        return "success";
    }

    /**
     * HTML内容邮件测试
     *
     * @return success
     * @throws MessagingException
     */
    @GetMapping("/html")
    public String html() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom(this.mailProperties.getUsername());
        messageHelper.setTo("511932633@qq.com");
        messageHelper.setSubject("HTML内容邮件测试");
        // 第二个参数表示是否html，设为true
        messageHelper.setText("<h1>HTML内容..</h1>", true);

        this.javaMailSender.send(message);
        return "success";
    }

    /**
     * 带附件的邮件测试
     *
     * @return success
     * @throws MessagingException
     */
    @GetMapping("/attach")
    public String attach() throws MessagingException {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        // 第二个参数表示是否开启multipart模式
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        messageHelper.setFrom(this.mailProperties.getUsername());
        messageHelper.setTo("511932633@qq.com");
        messageHelper.setSubject("带附件的邮件测试");
        // 第二个参数表示是否html，设为true
        messageHelper.setText("<h1>HTML内容..</h1>", true);
        messageHelper.addAttachment("附件名称",
                new ClassPathResource("wx.jpg"));

        this.javaMailSender.send(message);
        return "success";
    }

    /**
     * 内联附件的邮件测试
     *
     * @return success
     * @throws MessagingException
     */
    @GetMapping("/inline-attach")
    public String inlineAttach() throws MessagingException {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        // 第二个参数表示是否开启multipart模式
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom(this.mailProperties.getUsername());
        messageHelper.setTo("511932633@qq.com");
        messageHelper.setSubject("内联附件的邮件测试");
        // 第二个参数表示是否html，设为true
        messageHelper.setText("<h1>HTML内容..<img src=\"cid:attach\"/></h1>", true);
        messageHelper.addInline("attach", new ClassPathResource("wx.jpg"));

        this.javaMailSender.send(message);
        return "success";
    }

    /**
     * 内联附件的邮件测试
     *
     * @return success
     * @throws MessagingException
     */
    @GetMapping("/freemarker")
    public String freemarker() throws MessagingException, IOException, TemplateException {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        // 第二个参数表示是否开启multipart模式
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom(this.mailProperties.getUsername());
        messageHelper.setTo("511932633@qq.com");
        messageHelper.setSubject("基于freemarker模板的邮件测试");

        Map<String, Object> model = new HashMap<>();
        model.put("username", "itmuch");
        model.put("event", "IT牧场大事件");

        String content = FreeMarkerTemplateUtils.processTemplateIntoString(
                this.freemarkerConfiguration.getTemplate("mail.ftl"), model);

        // 第二个参数表示是否html，设为true
        messageHelper.setText(content, true);

        this.javaMailSender.send(message);
        return "success";
    }
}
