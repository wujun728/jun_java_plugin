package book.thread.pool;

/**
 * 定义任务的接口类
 */
public interface Task {

	/**
	 * 执行任务
	 * @throws Exception 执行过程中可能出现的异常
	 */
	public void perform() throws Exception;
}
