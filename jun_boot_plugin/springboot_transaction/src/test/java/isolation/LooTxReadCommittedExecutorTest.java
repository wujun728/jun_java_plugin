package isolation;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by qianjia on 2017/1/23.
 */
@SpringBootTest(classes = OracleTxIsolationTestApplication.class)
public class LooTxReadCommittedExecutorTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private LooTxReadCommittedExecutor executor;

  @Autowired
  private LooService looService;

  @Autowired
  private Flyway flyway;

  @AfterMethod
  public void clear() {
    looService.deleteAll();
  }

  @AfterClass
  public void cleanDb() {
    flyway.clean();
  }

  /**
   * 在 READ_COMMITTED 事务隔离级别下，不能防止 Unrepeatable Read的问题
   * 在这个例子里，第二次读取数据的Name发生了变化
   *
   * @throws Exception
   */
  @Test
  public void testRepeatableRead() throws Exception {
    looService.insert(1L, "Loo");
    List<Loo> result = executor.getById_update_getById(1L);

    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getId(), result.get(1).getId());
    assertEquals(result.get(0).getName(), "Loo");
    assertFalse(result.get(0).equals(result.get(1)));
  }

  /**
   * 在 READ_COMMITTED 事务隔离级别下，不能防止 Unrepeatable Read的问题
   * 在这个例子里，第二次读取数据返回null，因为被删掉里
   *
   * @throws Exception
   */
  @Test
  public void testRepeatableRead2() throws Exception {
    looService.insert(1L, "Loo");
    List<Loo> result = executor.getById_delete_getById(1L);

    assertEquals(result.size(), 2);
    assertNull(result.get(1));
    assertEquals(result.get(0).getName(), "Loo");

  }

  /**
   * 在 READ_COMMITTED 事务隔离级别下，不能防止 Phantom Read的问题
   * 在这个例子里，第二次读取的数据条目比第一次多
   *
   * @throws Exception
   */
  @Test
  public void testSerializable() throws Exception {
    looService.insert(1L, "Loo");
    looService.insert(2L, "Loo");
    List<List<Loo>> results = executor.getByName_insert_getByName("Loo");
    assertEquals(results.get(0).size(), 2);
    assertEquals(results.get(1).size(), 3);
  }

  /**
   * 在 READ_COMMITTED 事务隔离级别下，不能防止 Phantom Read的问题
   * 在这个例子里，第二次读取的数据和第一次第一次一样多，因为ID=1的数据发生了变化
   *
   * @throws Exception
   */
  @Test
  public void testSerializable2() throws Exception {
    looService.insert(1L, "Loo");
    looService.insert(2L, "Loo");
    List<List<Loo>> results = executor.getByName_insert_update_getByName("Loo");

    assertEquals(results.get(0).size(), 2);
    assertEquals(results.get(1).size(), 2);
  }

}
