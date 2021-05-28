package manual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * 这个例子用来测试手动rollback
 *
 * 相关文档：
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#transaction-declarative-rolling-back 章节结尾部分
 *
 * Created by qianjia on 2017/1/22.
 */
@SpringBootTest
@SpringBootApplication(exclude = FlywayAutoConfiguration.class)
public class ManualRollbackTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private FooService fooService;

  @Test
  public void testInsertNoRollback() throws Exception {

    fooService.insert(false, "Alice", "Mike", "Bob");
    List<String> names = fooService.getAll();

    assertEquals(names.size(), 3);
    assertTrue(names.contains("Alice"));
    assertTrue(names.contains("Mike"));
    assertTrue(names.contains("Bob"));

    fooService.deleteAll();
  }

  @Test
  public void testInsertRollback() throws Exception {

    fooService.insert(true, "Alice", "Mike", "Bob");
    List<String> names = fooService.getAll();

    assertEquals(names.size(), 0);

    fooService.deleteAll();

  }

}
