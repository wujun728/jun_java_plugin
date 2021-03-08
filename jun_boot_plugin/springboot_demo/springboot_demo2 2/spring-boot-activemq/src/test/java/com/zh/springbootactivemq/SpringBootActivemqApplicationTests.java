package com.zh.springbootactivemq;

import com.zh.springbootactivemq.model.User;
import com.zh.springbootactivemq.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootActivemqApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void sendString2QueueTest() throws JMSException {
        this.productService.sendQueueMsg("Hello World 2019");
    }

    @Test
    public void sendUser2QueueTest() throws JMSException {
        this.productService.sendQueueMsg(new User(1,"张三",27));
    }

    @Test
    public void send2WayString2QueueTest() throws JMSException {
        this.productService.send2WayQueueMsg("Hello World 2019");
    }

    @Test
    public void sendString2ACKQueueTest() throws JMSException, InterruptedException {
        this.productService.sendACKQueueMsg("Hello World 2019");
        //睡上20s防止服务器关闭接收不到延时消息
        TimeUnit.SECONDS.sleep(20);
    }

    @Test
    public void sendString2TopicTest() throws JMSException {
        this.productService.sendTopicMsg("Hello World 2019");
    }

    @Test
    public void sendUser2TopicTest() throws JMSException {
        this.productService.sendTopicMsg(new User(2,"李四",21));
    }

    @Test
    public void sendString2DelayTopicTest() throws JMSException, InterruptedException {
        this.productService.sendDelayTopicMsg("Hello World 2018");
        this.productService.sendDelayTopicMsg("Hello World 2019",5 * 1000);
        //睡上20s防止服务器关闭接收不到延时消息
        TimeUnit.SECONDS.sleep(20);
    }

}
