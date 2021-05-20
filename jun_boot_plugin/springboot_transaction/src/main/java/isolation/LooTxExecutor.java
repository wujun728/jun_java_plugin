package isolation;

import java.util.List;

/**
 * Created by qianjia on 2017/1/23.
 */
public interface LooTxExecutor {

  /**
   * 按以下顺序执行：
   * 1. getById，从数据库中获得Loo
   * 2. 在另外一个transaction里update
   * 3. 再次getById，从数据库中获得Loo
   *
   * @param id
   * @return 两次getById的结果
   */
  List<Loo> getById_update_getById(Long id);

  /**
   * 按以下顺序执行：
   * 1. getById，从数据库中获得Loo
   * 2. 在另外一个transaction里delete
   * 3. 再次getById，从数据库中获得Loo
   *
   * @param id
   * @return 两次getById的结果
   */
  List<Loo> getById_delete_getById(Long id);

  /**
   * 按以下顺序执行：
   * 1. getByName，从数据库中获得Loo list
   * 2. 在另外一个transaction里insert一个name一样的Loo
   * 3. 再次getByName，从数据库中获得Loo list
   *
   * @param name
   * @return 两次getByName的结果
   */
  List<List<Loo>> getByName_insert_getByName(String name);

  /**
   * 按以下顺序执行：
   * 1. getByName，从数据库中获得Loo list
   * 2. 在另外一个transaction里insert一个name一样的Loo
   * 3. 在另外一个transaction里update某一个Loo的name
   * 4. 再次getByName，从数据库中获得Loo list
   *
   * @param name
   * @return
   */
  List<List<Loo>> getByName_insert_update_getByName(String name);

}
