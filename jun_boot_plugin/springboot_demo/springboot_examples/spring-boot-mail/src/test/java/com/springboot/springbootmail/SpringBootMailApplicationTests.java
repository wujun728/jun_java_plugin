package com.springboot.springbootmail;

import com.springboot.springbootmail.service.MailService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMailApplicationTests {

    @Autowired
    MailService mailService;

    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void sendSimpleEmail() {
        mailService.sendSimpleEmail("inwsy@hotmail.com", "测试邮件题目", "测试邮件内容");
    }

    @Test
    public void sendHTMLTemplateMail() {
        Context context = new Context();
        context.setVariable("code", "123456");
        String emailHTMLContent = templateEngine.process("email", context);

        mailService.sendHTMLEmail("inwsy@hotmail.com", "测试 HTML 模版邮件", emailHTMLContent);
    }

    @Test
    public void sendAttachmentsMail() {

        String fileName = "图片.jpg";
        String filePath = "C:\\Users\\inwsy\\Downloads\\0370279582fe3e2a8012060c896a5dd.jpg";

        mailService.sendAttachmentsMail("inwsy@hotmail.com", "测试带附件的邮件", "详细请查阅附件", fileName, filePath);
    }

}
