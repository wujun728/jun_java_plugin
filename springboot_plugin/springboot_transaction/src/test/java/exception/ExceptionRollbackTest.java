package exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * 这个例子用来测试
 * 1. 当抛出unchecked exceptions的时候，transaction会自动回滚
 * 2. 当抛出checked exceptions的时候，transaction不会自动回滚
 *
 * 相关文档：
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#transaction-declarative-rolling-back
 *
 * Created by qianjia on 2017/1/22.
 */
@SpringBootTest
@SpringBootApplication(exclude = FlywayAutoConfiguration.class)
public class ExceptionRollbackTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private FooService fooService;

  /**
   * 测试insertThrowCheckedException, insertThrowUncheckedException是否正常执行
   *
   * @throws Exception
   */
  @Test
  public void testInsert() throws Exception {

    {
      fooService.insertThrowCheckedException(false, "Alice", "Mike", "Bob");
      List<String> names = fooService.getAll();

      assertEquals(names.size(), 3);
      assertTrue(names.contains("Alice"));
      assertTrue(names.contains("Mike"));
      assertTrue(names.contains("Bob"));
    }

    fooService.deleteAll();
    {
      fooService.insertThrowUncheckedException(false, "Alice", "Mike", "Bob");
      List<String> names = fooService.getAll();

      assertEquals(names.size(), 3);
      assertTrue(names.contains("Alice"));
      assertTrue(names.contains("Mike"));
      assertTrue(names.contains("Bob"));

      fooService.deleteAll();
    }

  }

  /**
   * 测试，当抛出 unchecked exceptions 的时候，transaction会自动回滚
   *
   * @throws Exception
   */
  @Test
  public void testInsertThrowUncheckedException() throws Exception {

    fooService.insertThrowUncheckedException(false, "Alice", "Mike", "Bob");

    Exception ex = null;
    try {
      fooService.insertThrowUncheckedException(true, "David", "Samuel", "Jack");
    } catch (Exception e) {
      ex = e;
    }

    assertNotNull(ex);
    assertEquals(ex.getClass(), FooRuntimeException.class);

    List<String> names = fooService.getAll();
    assertEquals(names.size(), 3);
    assertTrue(names.contains("Alice"));
    assertTrue(names.contains("Mike"));
    assertTrue(names.contains("Bob"));
    fooService.deleteAll();
  }

  /**
   * 测试，当抛出 checked exceptions 的时候，transaction不会自动回滚
   * 如果要对checked exceptions回滚，需要写 @Transactional(rollbackFor = XXX.class)
   *
   * @throws Exception
   */
  @Test
  public void testInsertThrowCheckedException() throws Exception {

    Exception ex = null;
    fooService.insertThrowCheckedException(false, "Alice", "Mike", "Bob");
    try {
      fooService.insertThrowCheckedException(true, "David", "Samuel", "Jack");
    } catch (Exception e) {
      ex = e;
    }

    assertNotNull(ex);
    assertEquals(ex.getClass(), FooException.class);

    List<String> names = fooService.getAll();
    assertEquals(names.size(), 6);
    assertTrue(names.contains("Alice"));
    assertTrue(names.contains("Mike"));
    assertTrue(names.contains("Bob"));

    // David Samuel Jack依然保存进去了
    assertTrue(names.contains("David"));
    assertTrue(names.contains("Samuel"));
    assertTrue(names.contains("Jack"));
    fooService.deleteAll();
  }

}
