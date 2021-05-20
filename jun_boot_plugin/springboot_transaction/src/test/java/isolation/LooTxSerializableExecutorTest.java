package isolation;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by qianjia on 2017/1/23.
 */
@SpringBootTest(classes = OracleTxIsolationTestApplication.class)
public class LooTxSerializableExecutorTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private LooTxSerializableExecutor executor;

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
   * 在 SERIALIZABLE 事务隔离级别下，能防止 Unrepeatable Read的问题
   * 在这个例子里，第二次读取数据和第一次一样
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
    assertEquals(result.get(0), result.get(1));
  }

  /**
   * 在 SERIALIZABLE 事务隔离级别下，能防止 Unrepeatable Read的问题
   * 在这个例子里，第二次读取数据和第一次一样
   *
   * @throws Exception
   */
  @Test
  public void testRepeatableRead2() throws Exception {
    looService.insert(1L, "Loo");
    List<Loo> result = executor.getById_delete_getById(1L);

    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getId(), result.get(1).getId());
    assertEquals(result.get(0).getName(), "Loo");
    assertEquals(result.get(0), result.get(1));

  }

  /**
   * 在 SERIALIZABLE 事务隔离级别下，能防止 Phantom Read的问题
   * 在这个例子里，第二次读取数据和第一次一样
   *
   * @throws Exception
   */
  @Test
  public void testSerializable() throws Exception {
    looService.insert(1L, "Loo");
    looService.insert(2L, "Loo");

    List<List<Loo>> results = executor.getByName_insert_getByName("Loo");

    assertEquals(results.get(0).size(), 2);
    assertEquals(results.get(1).size(), 2);
    assertTrue(results.get(0).containsAll(results.get(1)));
  }

  /**
   * 在 SERIALIZABLE 事务隔离级别下，不能防止 Phantom Read的问题
   * 在这个例子里，第二次读取数据和第一次一样
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
    assertTrue(results.get(0).containsAll(results.get(1)));

  }

}

