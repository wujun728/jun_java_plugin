package propagation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by qianjia on 2017/1/22.
 */
public abstract class FooTxAbstractExecutor implements FooTxExecutor {

  private final static Logger LOGGER = LoggerFactory.getLogger(FooTxAbstractExecutor.class);

  private final JdbcTemplate jdbcTemplate;

  public FooTxAbstractExecutor(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insert(BarTxExecutor barTxExecutor, boolean fooThrowException, boolean barThrowException, String... names) {

    for (String name : names) {
      LOGGER.debug("Insert Foo[" + name + "]");
      jdbcTemplate.update("insert into FOO(NAME) values (?)", name);
    }

    try {
      barTxExecutor.insert(barThrowException, names);
    } catch (Exception e) {
      LOGGER.error("Catch exception from BarTxExecutor. {}: {}", e.getClass().getName(), e.getMessage());
    }

    if (fooThrowException) {
      throw new FakeException("From " + getClass().getName());
    }

  }

}
