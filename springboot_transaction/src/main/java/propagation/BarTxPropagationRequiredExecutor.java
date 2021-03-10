package propagation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by qianjia on 2017/1/22.
 */
@Component
public class BarTxPropagationRequiredExecutor extends BarTxAbstractExecutor {

  public BarTxPropagationRequiredExecutor(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void insert(boolean throwException, String... names) {
    super.insert(throwException, names);
  }

  @Override
  public Propagation getPropagationMode() {
    return Propagation.REQUIRED;
  }

}
