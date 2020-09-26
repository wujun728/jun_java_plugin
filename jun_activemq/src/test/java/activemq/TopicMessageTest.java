package activemq;

import javax.jms.JMSException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jun.plugin.activemq_spring.PersonInfo;
import com.jun.plugin.activemq_spring.topic.TopicMessageProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext.xml")
public class TopicMessageTest {

    /**
     * 发布/订阅消息生产者
     */
    @Autowired
    private TopicMessageProducer topicMessageProducer;


    /**
     * 发送topic消息
     *  消息消费者可以有多个，一个消息可以被多个消费者接收到并处理。不保证消息一定会到达消费者的手中，所以，有的消费者可能会收不到消息，有的可以收到。
     * 
     * @throws JMSException
     */
    @Test
    public void testTopicMQ() throws JMSException {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setAddress("这是邮箱地址:abcd@qq.com");
        personInfo.setPwd("这是密码:123456");
        personInfo.setUserName("这是用户名:wujun");
        topicMessageProducer.sendTopicMessage(personInfo);
    }
    
}
