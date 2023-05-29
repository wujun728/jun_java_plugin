package com.jun.plugin.util4j.queue.queueExecutor.groupExecutor;

import java.util.Iterator;

import com.jun.plugin.util4j.queue.queueExecutor.QueueFactory;
import com.jun.plugin.util4j.queue.queueExecutor.executor.QueueExecutor;

/**
 * 队列组
 * @author juebanlin
 */
public interface IndexQueueGroupManager extends Iterable<QueueExecutor>{
	
	/**
	 * 设置队列别名
	 * @param solt
	 * @param alias
	 */
	public void setAlias(short index,String alias);
	
	/**
	 * 获取队列别名
	 * @param solt
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
	@Override
	Iterator<QueueExecutor> iterator();
	
	public long getToalCompletedTaskCount();
	
	public long getToalCompletedTaskCount(short index);
	
	public void setGroupEventListener(IndexGroupEventListener listener);
	
	public QueueFactory getQueueFactory();
	
	public void setQueueFactory(QueueFactory queueFactory);
	
	public static interface IndexGroupEventListener{
		/**
		 * 某队列的处理任务
		 * @param task
		 */
		public void onQueueHandleTask(short index,Runnable handleTask);
	}
}
