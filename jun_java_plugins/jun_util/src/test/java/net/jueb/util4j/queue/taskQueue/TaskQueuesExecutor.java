package net.jueb.util4j.queue.taskQueue;

import java.util.List;

/**
 * 任务队列组执行器
 * @author juebanlin
 */
public interface TaskQueuesExecutor{
	
	/**
	 * 执行队列任务
	 * @param queueName
	 * @param task
	 */
	public void execute(String queueName, Runnable task);
		
	/**
	 * 执行队列任务
	 * @param queue
	 * @param task
	 */
	public void execute(String queueName,Task task);
	
	/**
	 * 批量执行队列任务
	 * @param queue
	 * @param tasks
	 */
	public void execute(String queueName,List<Task> tasks);
	
	/**
	 * 打开一个队列
	 * 返回一个队列执行器
	 */
	public TaskQueueExecutor openQueue(String queueName);
	
	/**
	 * 关闭队列
	 * @param queueName
	 * @return
	 */
	public TaskQueue closeQueue(String queueName);
	
	/**
	 * 获取任务执行器
	 * @param queue
	 * @return
	 */
	public TaskQueueExecutor getQueueExecutor(String queueName);
	
	/**
	 * 获取队列公共任务转换器
	 * @return
	 */
	public TaskConvert getTaskConvert();
	
	/**
	 * 设置队列公共任务转换器(将覆盖队列特有的)
	 * @param taskConvert
	 */
	public void setTaskConvert(TaskConvert taskConvert);
}
