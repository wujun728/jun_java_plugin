package net.jueb.util4j.queue.taskQueue;

import java.util.Queue;

/**
 * 任务队列
 * @author juebanlin
 */
public interface TaskQueue extends Queue<Task>{
	
	/**
	 * 队列名称
	 * @return
	 */
	public String getQueueName();
}
