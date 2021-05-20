package progressbar;

/**
 * Created by qianjia on 2017/3/17.
 */
public interface ProgressBar {

  /**
   * 获得进度条ID，应该是唯一的
   *
   * @return
   */
  String getId();

  /**
   * 开始
   *
   * @param title 进度条标题
   */
  void start(String title);

  /**
   * 开始
   *
   * @param title 进度条标题
   * @param total 本次工作的总量
   */
  void start(String title, int total);

  /**
   * 设置标题
   *
   * @param title
   */
  void setTitle(String title);

  /**
   * 设置工作总量
   *
   * @param total
   */
  void setTotal(int total);

  /**
   * 前进一步，完成1个单位的工作
   */
  void forward();

  /**
   * 前进n步，完成n个单位的工作
   *
   * @param step n步
   */
  void forward(int step);

  /**
   * 后退一步，后退1个单位的工作
   */
  void rewind();

  /**
   * 后退n步，后退n个单位的工作
   *
   * @param step n步
   */
  void rewind(int step);

  /**
   * 标记当前进度条已经完成
   */
  void finish();

  /**
   * 完成n个单位的工作
   *
   * @param amount n个单位的工作
   */
  void setProgress(int amount);

  /**
   * 判断是否完成
   *
   * @return
   */
  boolean isFinished();

  /**
   * 获得标题
   *
   * @return
   */
  String getTitle();

  /**
   * 获得总量
   *
   * @return
   */
  int getTotal();

  /**
   * 获得当前进度
   *
   * @return
   */
  int getProgress();
}
