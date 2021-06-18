package isolation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by qianjia on 2017/1/23.
 */
@Component
public class LooServiceImpl implements LooService {

  private final JdbcTemplate jdbcTemplate;

  public LooServiceImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Transactional
  @Override
  public Loo getById(Long id) {
    List<Loo> loos = jdbcTemplate.query(
        "select ID, NAME from LOO where id = ? order by id",
        new Object[] { id },
        (rs, rowNum) -> new Loo(rs.getLong("ID"), rs.getString("NAME"))
    );
    if (loos.isEmpty()) {
      return null;
    }
    return loos.get(0);
  }

  /**
   * 注意这里的RequiresNew，这意味着肯定会开启一个不一样的事务
   *
   * @param id
   * @param name
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Override
  public void update(Long id, String name) {
    jdbcTemplate.update("update LOO set name =? where id = ?", name, id);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Override
  public void delete(Long id) {
    jdbcTemplate.update("delete from LOO where id = ?", id);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Override
  public void insert(Long id, String name) {
    jdbcTemplate.update("insert into LOO (ID, NAME) values(?, ?)", id, name);
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("delete from LOO");
  }

  @Transactional
  @Override
  public List<Loo> getByName(String name) {

    return jdbcTemplate.query(
        "select ID, NAME from LOO where NAME = ? order by id",
        new Object[] { name },
        (rs, rowNum) -> new Loo(rs.getLong("ID"), rs.getString("NAME"))
    );

  }

}
