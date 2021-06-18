package exception;

import java.util.List;

/**
 * Created by qianjia on 2017/1/22.
 */
public interface FooService {

  /**
   * 这个方法会抛出checked exception
   *
   * @param throwException 是否抛出异常
   * @param names
   * @throws FooException
   */
  void insertThrowCheckedException(boolean throwException, String... names) throws FooException;

  /**
   * 这个方法会抛出unchecked exception
   *
   * @param throwException 是否抛出异常
   * @param names
   * @throws FooRuntimeException
   */
  void insertThrowUncheckedException(boolean throwException, String... names) throws FooRuntimeException;

  List<String> getAll();

  void deleteAll();

}
