package isolation;

import java.util.Arrays;
import java.util.List;

/**
 * Created by qianjia on 2017/1/23.
 */
public abstract class LooTxAbstractExecutor implements LooTxExecutor {

  private LooService looService;

  public LooTxAbstractExecutor(LooService looService) {
    this.looService = looService;
  }

  @Override
  public List<Loo> getById_update_getById(Long id) {

    Loo loo = looService.getById(id);

    looService.update(loo.getId(), loo.getName() + "_Updated");

    Loo loo2 = looService.getById(id);
    return Arrays.asList(loo, loo2);

  }

  @Override
  public List<Loo> getById_delete_getById(Long id) {

    Loo loo = looService.getById(id);

    looService.delete(loo.getId());

    Loo loo2 = looService.getById(id);

    return Arrays.asList(loo, loo2);
  }

  @Override
  public List<List<Loo>> getByName_insert_getByName(String name) {

    List<Loo> loos = looService.getByName(name);
    looService.insert(1000L, name);
    List<Loo> loos2 = looService.getByName(name);
    return Arrays.asList(loos, loos2);

  }

  @Override
  public List<List<Loo>> getByName_insert_update_getByName(String name) {

    List<Loo> loos = looService.getByName(name);
    looService.insert(1000L, name);
    looService.update(loos.get(0).getId(), loos.get(0).getName() + "_Updated");
    List<Loo> loos2 = looService.getByName(name);
    return Arrays.asList(loos, loos2);

  }

}
