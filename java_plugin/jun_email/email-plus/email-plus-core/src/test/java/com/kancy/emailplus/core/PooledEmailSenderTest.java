package com.kancy.emailplus.core;

import com.kancy.emailplus.core.exception.EmailException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * PooledEmailSenderTest
 *
 * @author Wujun
 * @date 2020/2/20 5:13
 */
public class PooledEmailSenderTest {

    private EmailSender emailSender;

    @Before
    public void initPooledEmailSender(){
        PooledEmailSender simpleEmailSender = new PooledEmailSender();
        emailSender = simpleEmailSender;
    }

    @Ignore
    @Test
    public void sendTest(){
        try {
            emailSender.send(new SimpleEmail("tangdandan@vcredit.com", "test success"));
        } catch (EmailException e) {
            Assert.fail();
        }
    }

    @Test
    public void getSenderNameTest(){
        for (int i = 0; i <10 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() +" -> " + emailSender.getSenderName());
                }
            }).start();
        }
        Assert.assertNotNull(emailSender.getSenderName());
    }
}
