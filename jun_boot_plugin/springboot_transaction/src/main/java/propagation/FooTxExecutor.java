package propagation;

import org.springframework.transaction.annotation.Propagation;

/**
 * 操作FOO表的，Service
 * Created by qianjia on 2017/1/22.
 */
public interface FooTxExecutor {

  /**
   * 插入数据
   *
   * @param barTxExecutor     内部调用的{@link BarTxExecutor}
   * @param fooThrowException {@link FooTxExecutor}执行过程中是否抛出异常
   * @param barThrowException {@link BarTxExecutor}执行过程中是否抛出异常
   * @param names             数据
   */
  void insert(BarTxExecutor barTxExecutor, boolean fooThrowException, boolean barThrowException, String... names);

  /**
   * 本Service所使用的{@link Propagation}模式
   *
   * @return
   */
  Propagation getPropagationMode();

}
