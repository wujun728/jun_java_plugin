package com.kancy.emailplus.spring.boot;

import com.kancy.emailplus.spring.boot.config.EmailplusAutoConfiguration;
import com.kancy.emailplus.spring.boot.listener.event.RefreshEmailSenderEvent;
import com.kancy.emailplus.spring.boot.listener.event.SendEmailEvent;
import com.kancy.emailplus.spring.boot.message.EmailMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * RefreshSenderEventListenerTest
 *
 * @author Wujun
 * @date 2020/2/21 23:42
 */
@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmailplusAutoConfiguration.class,  RedisAutoConfiguration.class})
public class EventListenerTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void sendRefreshSenderEvent1(){
        applicationContext.publishEvent(new RefreshEmailSenderEvent(this, Collections.singleton("emailplus.sender.qq.host")));
    }
    @Test
    public void sendRefreshSenderEvent2(){
        applicationContext.publishEvent(new RefreshEmailSenderEvent(this, Collections.singleton("emailplus.test")));
    }
    @Test
    public void sendRefreshSenderEvent3(){
        applicationContext.publishEvent(new RefreshEmailSenderEvent());
    }

    @Test
    public void sendSendEmailEvent(){
        applicationContext.publishEvent(new SendEmailEvent(this, EmailMessage.create("test-a")));
    }
}
