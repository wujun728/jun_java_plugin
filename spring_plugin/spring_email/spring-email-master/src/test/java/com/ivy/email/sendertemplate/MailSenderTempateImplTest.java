package com.ivy.email.sendertemplate;

import com.ivy.email.BaseEmailTest;
import com.ivy.email.strategy.impl.HTMLStrategy;
import com.ivy.email.strategy.impl.TextStrategy;
import com.ivy.email.strategy.impl.ThymeleafStrategy;
import com.ivy.email.strategy.impl.VelocityStrategy;
import com.ivy.email.sendertemplate.impl.MailSenderTemplateImpl;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * Created by fangjie04 on 2016/12/3.
 */
public class MailSenderTempateImplTest extends BaseEmailTest {


    @Test
    public void testSendHTML() throws Exception {
        MailSenderTemplateImpl template = context.getBean(MailSenderTemplateImpl.class);
        String text = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "<style>\n" +
                "#header {\n" +
                "    background-color:black;\n" +
                "    color:white;\n" +
                "    text-align:center;\n" +
                "    padding:5px;\n" +
                "}\n" +
                "#nav {\n" +
                "    line-height:30px;\n" +
                "    background-color:#eeeeee;\n" +
                "    height:300px;\n" +
                "    width:100px;\n" +
                "    float:left;\n" +
                "    padding:5px;\t      \n" +
                "}\n" +
                "#section {\n" +
                "    width:350px;\n" +
                "    float:left;\n" +
                "    padding:10px;\t \t \n" +
                "}\n" +
                "#footer {\n" +
                "    background-color:black;\n" +
                "    color:white;\n" +
                "    clear:both;\n" +
                "    text-align:center;\n" +
                "   padding:5px;\t \t \n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "<div id=\"header\">\n" +
                "<h1>City Gallery</h1>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"nav\">\n" +
                "London<br>\n" +
                "Paris<br>\n" +
                "Tokyo<br>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"section\">\n" +
                "<h2>London</h2>\n" +
                "<p>\n" +
                "London is the capital city of England. It is the most populous city in the United Kingdom,\n" +
                "with a metropolitan area of over 13 million inhabitants.\n" +
                "</p>\n" +
                "<p>\n" +
                "Standing on the River Thames, London has been a major settlement for two millennia,\n" +
                "its history going back to its founding by the Romans, who named it Londinium.\n" +
                "</p>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"footer\">\n" +
                "Copyright ? W3Schools.com\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        vo.setEmailContent(text);
        template.setStrategy(new HTMLStrategy()).sendMail(vo);
    }

    @Test
    public void testSendText() throws Exception {
        MailSenderTemplate template = context.getBean(MailSenderTemplateImpl.class);
        template.setStrategy(new TextStrategy()).sendMail(vo);
    }


    @Test
    public void testSendVelocity() throws Exception {
        MailSenderTemplate mailSenderTemplate = context.getBean(MailSenderTemplateImpl.class);
        VelocityEngine velocityEngine = context.getBean(VelocityEngine.class);
        VelocityContext ctx = new VelocityContext();
        ctx.put("name", "Jack");
        mailSenderTemplate.setStrategy(new VelocityStrategy(ctx, "/velocity/velocityTemplate.vm")
                .setVelocityEngine(velocityEngine)).sendMail(vo);
    }

    @Test
    public void testSendThymeleaf() throws Exception {
        MailSenderTemplate mailSenderTemplate = context.getBean(MailSenderTemplateImpl.class);
        SpringTemplateEngine springTemplateEngine = context.getBean(SpringTemplateEngine.class);
        Context context = new Context();
        context.setVariable("name", "fangjie");
        context.setVariable("text", "使用Thymeleaf构建Email消息");
        mailSenderTemplate.setStrategy(new ThymeleafStrategy(context, "/thymeleaf/emailTemplate.html")
                .setSpringTemplateEngine(springTemplateEngine)).sendMail(vo);
    }

}
