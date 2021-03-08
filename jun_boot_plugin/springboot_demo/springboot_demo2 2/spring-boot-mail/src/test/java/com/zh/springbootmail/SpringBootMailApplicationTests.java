package com.zh.springbootmail;

import com.zh.springbootmail.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMailApplicationTests {

    @Autowired
    private MailService mailService;

    @Test
    public void simpleMailTest() {
        String to = "286910682@qq.com";
        String subject = "测试邮件1";
        String text = "Hello World";
        this.mailService.sendEmail(to,subject,text);
    }

    @Test
    public void fileMailTest() {
        String to = "286910682@qq.com";
        String subject = "测试邮件2";
        String text = "Hello World 2019";
        String filePath = "D:\\工作\\资料\\资料\\详版报告样本.txt";
        this.mailService.sendEmailWithFile(to,subject,text,filePath);
    }

    @Test
    public void htmlMailTest() {
        String to = "286910682@qq.com";
        String subject = "测试邮件3";
        String text = "<html><body><h1>这是一封html邮件,啦啦啦</h1></body></html>";
        this.mailService.sendWithHtml(to,subject,text);
    }

    @Test
    public void htmlFileMailTest() {
        String to = "zhanghang9202@163.com";
        String subject = "测试邮件4";
        String text = "<html>" +
                "<h1>这是一封有图有真相的html邮件,啦啦啦</h1>" +
                "<img style='width:200px;height:200px' src='cid:picture1'>" +
                "<img style='width:200px;height:200px' src='cid:picture2'>" +
                "</html>";
        Map<String,String> map = new HashMap<>(16);
        map.put("picture1","D:\\direct.jpg");
        map.put("picture2","D:\\topic.jpg");
        this.mailService.sendWithImageHtml(to,subject,text,map);
    }
}
