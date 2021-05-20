package propagation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by qianjia on 2017/1/22.
 */
@Component
public class FooTxPropagationNotSupportedExecutor extends FooTxAbstractExecutor {

  public FooTxPropagationNotSupportedExecutor(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  @Override
  public void insert(BarTxExecutor barTxExecutor, boolean fooThrowException, boolean barThrowException, String... names) {
    super.insert(barTxExecutor, fooThrowException, barThrowException, names);
  }

  @Override
  public Propagation getPropagationMode() {
    return Propagation.NOT_SUPPORTED;
  }

}
