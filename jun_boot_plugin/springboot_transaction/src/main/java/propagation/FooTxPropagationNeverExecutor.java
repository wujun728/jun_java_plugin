package propagation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by qianjia on 2017/1/22.
 */
@Component
public class FooTxPropagationNeverExecutor extends FooTxAbstractExecutor {

  public FooTxPropagationNeverExecutor(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Transactional(propagation = Propagation.NEVER)
  @Override
  public void insert(BarTxExecutor barTxExecutor, boolean fooThrowException, boolean barThrowException, String... names) {
    super.insert(barTxExecutor, fooThrowException, barThrowException, names);
  }

  @Override
  public Propagation getPropagationMode() {
    return Propagation.NEVER;
  }

}
