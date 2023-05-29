package net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.multithread.disruptor;

import java.util.ArrayList;
import java.util.List;

import net.jueb.util4j.queue.taskQueue.Task;

/**
 * 队列事件
 * @author juebanlin
 */
class QueueEvent {
	
	/**
	 * 队列名称
	 */
	private String queueName;
	
	private final List<Task> tasks=new ArrayList<>();

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	@Override
	public String toString() {
		return "QueueEvent [queueName=" + queueName + ", tasks=" + tasks + "]";
	}
}
