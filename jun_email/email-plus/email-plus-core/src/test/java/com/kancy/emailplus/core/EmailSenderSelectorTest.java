package com.kancy.emailplus.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * EmailSenderSelectorTest
 *
 * @author kancy
 * @date 2020/2/20 3:04
 */
public class EmailSenderSelectorTest {

    @Test
    public void testPollingEmailSenderSelectorInThread(){
        final PollingEmailSenderSelector selector = new PollingEmailSenderSelector();
        final List<EmailSender> list = getEmailSenders();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(selector.findEmailSender(list));
                }
            });
            thread.start();
        }
        Assert.assertNotNull(selector.findEmailSender(list));
        Assert.assertEquals(3, list.size());
        sleep();
    }

    @Test
    public void testPollingEmailSenderSelector(){
        PollingEmailSenderSelector selector = new PollingEmailSenderSelector();
        List<EmailSender> list = getEmailSenders();
        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(list.get(i), selector.findEmailSender(list));
        }
    }

    @Test
    public void testRandomEmailSenderSelector(){
        RandomEmailSenderSelector selector = new RandomEmailSenderSelector();
        List<EmailSender> list = getEmailSenders();
        selector.findEmailSender(list);
        selector.findEmailSender(list);
        selector.findEmailSender(list);
        Assert.assertNotNull(selector.findEmailSender(list));
    }

    private List<EmailSender> getEmailSenders() {
        List<EmailSender> list = new ArrayList<EmailSender>(){{
            add(new SimpleEmailSender());
            add(new SimpleEmailSender());
            add(new SimpleEmailSender());
        }};
        return list;
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
