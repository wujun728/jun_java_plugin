package me.chanjar.springjmslearn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@SpringBootTest(classes = JmsConfiguration.class)
public class SingleConnectionFactoryTest extends AbstractTestNGSpringContextTests {

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

    SingleConnectionFactory singleCf = new SingleConnectionFactory(connectionFactory);
    Connection conn1 = singleCf.createConnection();
    Connection conn2 = singleCf.createConnection();
    // 实际上这两个connection是proxy，不过其target connection都是同一个
    assertEquals(conn1, conn2);

  }

  /**
   * 测试并没有缓存Session
   *
   * @throws JMSException
   */
  @Test
  public void testNotCachingSession() throws JMSException {

    SingleConnectionFactory singleCf = new SingleConnectionFactory(connectionFactory);
    Connection connection = singleCf.createConnection();

    Session session1 = connection.createSession();
    session1.close();
    Session session2 = connection.createSession();
    session2.close();
    // session不是proxy，且每次都是new session
    assertNotEquals(session1, session2);

  }

}
