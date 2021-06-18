package propagation;

import org.springframework.transaction.annotation.Propagation;

/**
 * 操作BAR表的，Service
 * Created by qianjia on 2017/1/22.
 */
public interface BarTxExecutor {

  /**
   * 插入数据
   *
   * @param throwException 执行过程中是否抛出异常
   * @param names
   */
  void insert(boolean throwException, String... names);

  /**
   * 本Service所使用的{@link Propagation}模式
   *
   * @return
   */
  Propagation getPropagationMode();

}
