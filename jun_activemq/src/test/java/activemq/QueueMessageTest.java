package activemq;

import javax.jms.JMSException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jun.plugin.activemq_spring.PersonInfo;
import com.jun.plugin.activemq_spring.queue.QueueMessageProducer;

/**
 * 测试点对点消息发送
 * 
 * 作者: zhoubang 
 * 日期：2015年9月28日 上午10:17:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext.xml")
public class QueueMessageTest {

    /**
     * 点对点消息生产者
     */
    @Autowired
    private QueueMessageProducer queueMessageProducer;

    
    /**
     * 发送queue消息
     * 
     *  queue消费者可以有多个，但是最终只能有一个消费者接收到消息并进行处理，可以说是一次性，接收到之后消息就不存在了。
     *  PTP消息，可以保证消息一定会到达消费者的手中。
     *  消息接收成功后，会通知容器进行下一步处理。
     * 
     * 作者: zhoubang 
     * 日期：2015年9月28日 上午10:18:45
     * @throws JMSException
     */
    @Test
    public void testQueueMQ() throws JMSException {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setAddress("这是邮箱地址:842324724@qq.com");
        personInfo.setPwd("这是密码:123456");
        personInfo.setUserName("这是用户名:zhoubang");
        queueMessageProducer.sendQueueMessage(personInfo);
    }
}
