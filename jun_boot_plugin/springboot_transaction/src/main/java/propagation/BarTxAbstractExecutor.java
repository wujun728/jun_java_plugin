package propagation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by qianjia on 2017/1/22.
 */
public abstract class BarTxAbstractExecutor implements BarTxExecutor {

  private final static Logger LOGGER = LoggerFactory.getLogger(BarTxAbstractExecutor.class);

  private final JdbcTemplate jdbcTemplate;

  public BarTxAbstractExecutor(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void insert(boolean throwException, String... names) {

    for (String name : names) {
      LOGGER.debug("Insert Bar[" + name + "]");
      jdbcTemplate.update("insert into BAR(NAME) values (?)", name);
    }

    if (throwException) {
      throw new FakeException("From " + getClass().getName());
    }

  }

}
