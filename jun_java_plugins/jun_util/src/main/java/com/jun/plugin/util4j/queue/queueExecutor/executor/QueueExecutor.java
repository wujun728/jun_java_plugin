package com.jun.plugin.util4j.queue.queueExecutor.executor;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 任务队列执行器
 * @author juebanlin
 */
public interface QueueExecutor extends Executor{
	
	/**
	 * 获取队列名称
	 * @return
	 */
	public String getQueueName();
	
	/**
	 * 执行任务
	 * @param task
	 */
	public void execute(Runnable task);
	
	/**
	 * 批量执行任务
	 * @param tasks
	 */
	public void execute(List<Runnable> tasks);
	
	/**
	 * 队列大小
	 * @return
	 */
	public int size();
}
