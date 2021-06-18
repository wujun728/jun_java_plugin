package manual;

import exception.FooException;

import java.util.List;

/**
 * Created by qianjia on 2017/1/22.
 */
public interface FooService {

  /**
   * @param names
   * @throws FooException
   */
  void insert(boolean rollback, String... names) throws FooException;

  List<String> getAll();

  void deleteAll();

}
