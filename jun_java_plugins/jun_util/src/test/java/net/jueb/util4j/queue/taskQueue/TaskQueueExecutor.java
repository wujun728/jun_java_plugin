package net.jueb.util4j.queue.taskQueue;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 任务队列执行器
 * @author juebanlin
 */
@Deprecated
public interface TaskQueueExecutor extends Executor{
	
	/**
	 * 获取队列名称
	 * @return
	 */
	public String getQueueName();
	
	/**
	 * 执行任务
	 * @param task
	 */
	public void execute(Task task);
	
	/**
	 * 批量执行任务
	 * @param tasks
	 */
	public void execute(List<Task> tasks);
	
	/**
	 * 获取任务转换器
	 * @return
	 */
	public TaskConvert getTaskConvert();
	
	/**
	 * 设置任务转换器
	 * @param taskConvert
	 */
	public void setTaskConvert(TaskConvert taskConvert);
}
