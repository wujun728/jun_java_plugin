package net.jueb.util4j.queue.taskQueue.impl;

import java.util.concurrent.LinkedBlockingQueue;

import net.jueb.util4j.queue.taskQueue.Task;
import net.jueb.util4j.queue.taskQueue.TaskQueue;

public class BlockingTaskQueue extends LinkedBlockingQueue<Task> implements TaskQueue{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6158476112928562757L;

	private final String name;
	
	public BlockingTaskQueue(String name) {
		if (name == null)
            throw new NullPointerException();
		this.name=name;
	}
	
	@Override
	public final String getQueueName() {
		return name;
	}
}
