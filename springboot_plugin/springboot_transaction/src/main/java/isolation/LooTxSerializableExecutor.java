package isolation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 操作的事务隔离级别是 READ_COMMITTED
 * Created by qianjia on 2017/1/23.
 */
@Component
public class LooTxSerializableExecutor extends LooTxAbstractExecutor {

  public LooTxSerializableExecutor(LooService looService) {
    super(looService);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  @Override
  public List<Loo> getById_update_getById(Long id) {
    return super.getById_update_getById(id);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  @Override
  public List<Loo> getById_delete_getById(Long id) {
    return super.getById_delete_getById(id);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  @Override
  public List<List<Loo>> getByName_insert_getByName(String name) {
    return super.getByName_insert_getByName(name);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  @Override
  public List<List<Loo>> getByName_insert_update_getByName(String name) {
    return super.getByName_insert_update_getByName(name);
  }

}
