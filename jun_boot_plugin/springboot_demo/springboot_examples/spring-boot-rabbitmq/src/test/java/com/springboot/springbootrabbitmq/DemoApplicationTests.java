package com.springboot.springbootrabbitmq;

import com.springboot.springbootrabbitmq.fanout.FanoutSend;
import com.springboot.springbootrabbitmq.many.SimpleManySend;
import com.springboot.springbootrabbitmq.many.SimpleManySend1;
import com.springboot.springbootrabbitmq.simple.SimpleSend;

import com.springboot.springbootrabbitmq.topic.TopicSend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    SimpleSend simpleSend;

    @Autowired
    SimpleManySend simpleManySend;

    @Autowired
    SimpleManySend1 simpleManySend1;

    @Autowired
    TopicSend topicSend;

    @Autowired
    FanoutSend fanoutSend;

    @Test
    public void simpleSend() {
        simpleSend.send();
    }

    @Test
    public void simpleOneSend() {
        for (int i = 0; i < 100; i ++) {
            simpleManySend.send(i);
        }
    }

    @Test
    public void simpleManySend() {
        for (int i = 0; i < 100; i ++) {
            simpleManySend.send(i);
            simpleManySend1.send(i);
        }
    }

    @Test
    public void topicSend1() {
        topicSend.send1();
    }

    @Test
    public void topicSend2() {
        topicSend.send2();
    }

    @Test
    public void fanoutSend() {
        fanoutSend.send();
    }

}
