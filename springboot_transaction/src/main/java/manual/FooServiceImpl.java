package manual;

import exception.FooException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * Created by qianjia on 2017/1/22.
 */
@Component
public class FooServiceImpl implements FooService {

  private final static Logger LOGGER = LoggerFactory.getLogger(FooServiceImpl.class);

  private final JdbcTemplate jdbcTemplate;

  public FooServiceImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  @Transactional(isolation = Isolation.DEFAULT)
  public void insert(boolean rollback, String... names) throws FooException {

    for (String name : names) {
      LOGGER.info("Insert Foo[" + name + "]");
      jdbcTemplate.update("insert into FOO(NAME) values (?)", name);
    }

    if (rollback) {
      // trigger rollback programmatically
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

  }

  @Override
  public List<String> getAll() {
    return jdbcTemplate.query("select NAME from FOO", (rs, rowNum) -> rs.getString("NAME"));
  }

  @Override
  public void deleteAll() {

    jdbcTemplate.update("delete from FOO");

  }

}
