package com.jun.plugin.util4j.queue.queueExecutor.executor.impl;

import com.jun.plugin.util4j.queue.queueExecutor.RunnableQueue;
import com.jun.plugin.util4j.queue.queueExecutor.executor.QueueExecutor;

public abstract class AbstractQueueExecutorBase extends AbstractQueueExecutor implements QueueExecutor{

	private final RunnableQueue queue;
	private final String queueName;
	
	public AbstractQueueExecutorBase(RunnableQueue queue,String queueName) {
		if (queue == null || queueName==null)
            throw new NullPointerException();
		this.queue=queue;
		this.queueName=queueName;
	}
	
	@Override
	public final void execute(Runnable command) {
		if (command == null)
            throw new NullPointerException();
		queue.offer(command);
	}

	@Override
	public final String getQueueName() {
		return queueName;
	}

	public final RunnableQueue getQueue() {
		return queue;
	}
	
	@Override
	public final int size() {
		return queue.size();
	}
}
