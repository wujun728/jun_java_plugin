package propagation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by qianjia on 2017/1/22.
 */
@Component
public class BarServiceImpl implements BarService {

  private final JdbcTemplate jdbcTemplate;

  public BarServiceImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<String> getAll() {
    return jdbcTemplate.query("select NAME from BAR", (rs, rowNum) -> rs.getString("NAME"));
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("delete from BAR");
  }

}
