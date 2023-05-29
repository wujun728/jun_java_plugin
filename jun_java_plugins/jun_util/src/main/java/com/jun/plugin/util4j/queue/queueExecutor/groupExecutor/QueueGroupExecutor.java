package com.jun.plugin.util4j.queue.queueExecutor.groupExecutor;

import java.util.Iterator;
import java.util.List;

import com.jun.plugin.util4j.queue.queueExecutor.executor.QueueExecutor;

/**
 * 任务队列组执行器
 * @author juebanlin
 */
public interface QueueGroupExecutor extends QueueGroupExecutorBase{
	
	/**
	 * 执行队列任务
	 * @param index 队列号
	 * @param task 任务
	 */
	public void execute(short index,Runnable task);
	
	/**
	 * 批量执行队列任务
	 * @param index 队列号
	 * @param tasks 批量任务
	 */
	public void execute(short index,List<Runnable> tasks);
	
	/**
	 * 设置队列别名
	 * @param index
	 * @param alias
	 */
	public void setAlias(short index,String alias);
	
	/**
	 * 获取队列别名
	 * @param index
	 */
	public String getAlias(short index);
	
	/**
	 * 获取任务执行器,此队列的名字等于队列别名
	 * @param queue
	 * @return
	 */
	public QueueExecutor getQueueExecutor(short index);
	
	
	/**
	 * 迭代执行器
	 */
	Iterator<QueueExecutor> indexIterator();
	
	public void execute(String key,Runnable task);
	public void execute(String key,List<Runnable> tasks);
	public void setAlias(String key,String alias);
	public String getAlias(String key);
	public QueueExecutor getQueueExecutor(String key);
	Iterator<QueueExecutor> keyIterator();
}
