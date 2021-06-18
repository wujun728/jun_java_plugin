package com.kancy.emailplus.core;

import com.kancy.emailplus.core.exception.EmailException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * SimpleEmailSenderTest
 *
 * @author Wujun
 * @date 2020/2/20 1:36
 */
public class SimpleEmailSenderTest {

    private EmailSender emailSender;

    private EmailSender qqSimpleEmailSender = new QQSimpleEmailSender("793272861@qq.com", "ENC(0Xn6xLVRo/JnOaY9e9RwtcFf2mKIw5tmB9W67pVCOR0=)");

    @Before
    public void initSimpleEmailSender(){
        SimpleEmailSender simpleEmailSender = new SimpleEmailSender();
        simpleEmailSender.setHost("smtp.263.net");
        simpleEmailSender.setUsername("fkydssj@vcredit.com");
        simpleEmailSender.setPassword("ENC(02Hz0uEM+qgrf08oQ6jWZuRSyUAga1Z/VyQ=)");
        emailSender = simpleEmailSender;
    }

    @Test
    public void testSimpleEmailSender(){
        try {
            emailSender.send(new SimpleEmail("793272861@qq.com", "test success"));
        } catch (EmailException e) {
            Assert.fail();
        }
    }

    @Test
    public void testQQSimpleEmailSender(){
        try {
            qqSimpleEmailSender.send(new SimpleEmail("huangchengkang@vcredit.com", "Handsome Guy!"));
        } catch (EmailException e) {
            Assert.fail();
        }
    }
}
