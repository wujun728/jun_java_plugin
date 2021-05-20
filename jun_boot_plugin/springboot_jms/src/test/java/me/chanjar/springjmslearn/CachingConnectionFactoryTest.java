package me.chanjar.springjmslearn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.jms.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@SpringBootTest(classes = JmsConfiguration.class)
public class CachingConnectionFactoryTest extends AbstractTestNGSpringContextTests {

  /**
   * 这是JMS Provider提供的原生的ConnectionFactory
   */
  @Autowired
  private ConnectionFactory connectionFactory;

  /**
   * 测试缓存单个Connection
   *
   * @throws JMSException
   */
  @Test
  public void testCachingSingleConnection() throws JMSException {

    CachingConnectionFactory cachingCf = new CachingConnectionFactory(connectionFactory);
    Connection conn1 = cachingCf.createConnection();
    Connection conn2 = cachingCf.createConnection();
    // 实际上这两个connection是proxy，不过其target connection都是同一个
    assertEquals(conn1, conn2);

  }

  /**
   * 测试缓存Session
   *
   * @throws JMSException
   */
  @Test
  public void testCachingSession() throws JMSException {

    CachingConnectionFactory cachingCf = new CachingConnectionFactory(connectionFactory);
    Connection connection = cachingCf.createConnection();

    // 默认缓存1个session，必须等前一个session close之后，才会被另一个createSession复用
    Session session1 = connection.createSession();
    session1.close();
    Session session2 = connection.createSession();
    session2.close();
    // session是proxy，复用同一个target session
    assertEquals(session1, session2);

  }

  /**
   * 测试根据的Acknowledge Mode缓存session<br>
   * 所以CachingConnectionFactory的session cache size是ack mode->session list里session list的大小。
   *
   * @throws JMSException
   */
  @Test
  public void testCachingDifferentAckModeSession() throws JMSException {

    CachingConnectionFactory cachingCf = new CachingConnectionFactory(connectionFactory);
    Connection connection = cachingCf.createConnection();

    // 默认缓存1个session，必须等前一个session close之后，才会被另一个createSession复用
    Session session1 = connection.createSession(Session.AUTO_ACKNOWLEDGE);
    session1.close();
    Session session2 = connection.createSession(Session.CLIENT_ACKNOWLEDGE);
    session2.close();
    // 因为创建session的Acknowledge Mode不同，因此这是两个不一样的session实例
    assertNotEquals(session1, session2);

    // CachingConnectionFactory实际上是根据session
    Session session11 = connection.createSession(Session.AUTO_ACKNOWLEDGE);
    session11.close();
    assertEquals(session1, session11);

    Session session21 = connection.createSession(Session.CLIENT_ACKNOWLEDGE);
    session21.close();
    assertEquals(session2, session21);

  }

  /**
   * 测试超出session cache size（默认值是1）连续创建多个session，获得的不是同一个实例
   *
   * @throws JMSException
   */
  @Test
  public void testExceedSessionCacheSize() throws JMSException {

    CachingConnectionFactory cachingCf = new CachingConnectionFactory(connectionFactory);
    Connection connection = cachingCf.createConnection();

    Session session1 = connection.createSession();
    Session session2 = connection.createSession();

    assertNotEquals(session1, session2);
    session1.close();
    session2.close();

  }

  /**
   * 测试MessageProducer缓存在session中
   *
   * @throws JMSException
   */
  @Test
  public void testCachingProducer() throws JMSException {

    CachingConnectionFactory cachingCf = new CachingConnectionFactory(connectionFactory);
    Connection connection = cachingCf.createConnection();

    Session session1 = connection.createSession();
    Queue queue = session1.createQueue("tmp-queue");

    // 实际上这里返回的是 CachedMessageProducer
    MessageProducer producer1 = session1.createProducer(queue);
    MessageProducer producer2 = session1.createProducer(queue);
    // 因为技术限制，只能根据toString()来做比较
    assertEquals(producer1.toString(), producer2.toString());

    // 下面证明只要获得了相同的session实例，其已经缓存的MessageProducer是不会丢失的
    session1.close();
    Session session2 = connection.createSession();
    MessageProducer producer3 = session2.createProducer(queue);
    assertEquals(producer1.toString(), producer3.toString());
  }

}
