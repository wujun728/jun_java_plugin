package com.jun.plugin.me;

import com.jun.plugin.plugin.me.MyEmail;
import com.jun.plugin.plugin.me.SendMailException;
//import jetbrick.template.JetEngine;
//import jetbrick.template.JetTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.jun.plugin.plugin.me.MyEmail.SMTP_ENT_QQ;
import static com.jun.plugin.plugin.me.MyEmail.SMTP_QQ;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送邮件测试
 *
 * @author biezhi
 * 2017/5/30
 */
public class MyEmailTest {

    // 该邮箱修改为你需要测试的邮箱地址
    private static final String TO_EMAIL = "xiaojiejie@gmail.com";

    @Before
    public void before() {
        // 配置，一次即可
        MyEmail.config(SMTP_QQ(false), "xxx@qq.com", "*******");
        // 如果是企业邮箱则使用下面配置
        MyEmail.config(SMTP_ENT_QQ(false), "xxx@qq.com", "*******");
    }

    @Test
    public void testSendText() throws SendMailException {
        MyEmail.subject("这是一封测试TEXT邮件")
                .from("小姐姐的邮箱")
                .to(TO_EMAIL)
                .text("信件内容")
                .send();
        Assert.assertTrue(true);
    }

    @Test
    public void testSendHtml() throws SendMailException {
        MyEmail.subject("这是一封测试HTML邮件")
                .from("小姐姐的邮箱")
                .to(TO_EMAIL)
                .html("<h1 font=red>信件内容</h1>")
                .send();
        Assert.assertTrue(true);
    }

    @Test
    public void testSendAttach() throws SendMailException {
        MyEmail.subject("这是一封测试附件邮件")
                .from("小姐姐的邮箱")
                .to(TO_EMAIL)
                .html("<h1 font=red>信件内容</h1>")
                .attach(new File("/Users/biezhi/Downloads/hello.jpeg"), "测试图片.jpeg")
                .send();
        Assert.assertTrue(true);
    }

    @Test
    public void testSendAttachURL() throws SendMailException, MalformedURLException {
        MyEmail.subject("这是一封测试网络资源作为附件的邮件")
                .from("小姐姐的邮箱")
                .to(TO_EMAIL)
                .html("<h1 font=red>信件内容</h1>")
                .attachURL(new URL("https://avatars1.githubusercontent.com/u/2784452?s=40&v=4"), "测试图片.jpeg")
                .send();
        Assert.assertTrue(true);
    }

//    @Test
//    public void testPebble() throws IOException, PebbleException, SendMailException {
//        PebbleEngine   engine           = new PebbleEngine.Builder().build();
//        PebbleTemplate compiledTemplate = engine.getTemplate("register.html");
//
//        Map<String, Object> context = new HashMap<String, Object>();
//        context.put("username", "biezhi");
//        context.put("email", "admin@biezhi.me");
//
//        Writer writer = new StringWriter();
//        compiledTemplate.evaluate(writer, context);
//
//        String output = writer.toString();
//        System.out.println(output);
//
//        MyEmail.subject("这是一封测试Pebble模板邮件")
//                .from("小姐姐的邮箱")
//                .to(TO_EMAIL)
//                .html(output)
//                .send();
//        Assert.assertTrue(true);
//    }

//    @Test
//    public void testJetx() throws SendMailException {
//        JetEngine   engine   = JetEngine.create();
//        JetTemplate template = engine.getTemplate("/register.jetx");
//
//        Map<String, Object> context = new HashMap<String, Object>();
//        context.put("username", "biezhi");
//        context.put("email", "admin@biezhi.me");
//        context.put("url", "<a href='http://biezhi.me'>https://biezhi.me/active/asdkjajdasjdkaweoi</a>");
//
//        StringWriter writer = new StringWriter();
//        template.render(context, writer);
//        String output = writer.toString();
//        System.out.println(output);
//
//        MyEmail.subject("这是一封测试Jetx模板邮件")
//                .from("小姐姐的邮箱")
//                .to(TO_EMAIL)
//                .html(output)
//                .send();
//        Assert.assertTrue(true);
//    }

}