package propagation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by qianjia on 2017/1/22.
 */
@Component
public class FooServiceImpl implements FooService {

  private final JdbcTemplate jdbcTemplate;

  public FooServiceImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
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
