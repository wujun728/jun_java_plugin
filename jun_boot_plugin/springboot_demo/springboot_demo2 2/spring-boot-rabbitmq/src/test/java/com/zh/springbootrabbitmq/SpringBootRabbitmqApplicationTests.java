package com.zh.springbootrabbitmq;

import com.zh.springbootrabbitmq.config.RabbitMQProduct;
import com.zh.springbootrabbitmq.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRabbitmqApplicationTests {

    @Autowired
    private RabbitMQProduct rabbitMQProduct;

    @Test
    public void fanoutExchangeTest() {
        rabbitMQProduct.sendFanoutMsg(new User("张三"));
    }

    @Test
    public void directExchangeTest() {
        rabbitMQProduct.sendDirectMsg(new User("李四"),"bbb");
    }

    @Test
    public void topicExchangeTest() {
        rabbitMQProduct.sendTopicMsg(new User("王五"),"aaa.bbb");
        rabbitMQProduct.sendTopicMsg(new User("赵六"),"aaa.bbb.ccc");
    }

    @Test
    public void alternateExchangeTest() {
        rabbitMQProduct.sendAlternateMsg(new User("田七"),"aaa");
        rabbitMQProduct.sendAlternateMsg(new User("庞八"),"bbb");
    }

    @Test
    public void delayTTLDLXExchangeTest() throws InterruptedException {
        rabbitMQProduct.sendTTLDLXDelayMsg(new User("陈九"));
        TimeUnit.SECONDS.sleep(15);
    }

    @Test
    public void delayExchangeTest() throws InterruptedException {
        rabbitMQProduct.sendDelayMsg(new User("周十"),"aaa",10000);
        rabbitMQProduct.sendDelayMsg(new User("吴十一"),"aaa",5000);
        TimeUnit.SECONDS.sleep(15);
    }

}
