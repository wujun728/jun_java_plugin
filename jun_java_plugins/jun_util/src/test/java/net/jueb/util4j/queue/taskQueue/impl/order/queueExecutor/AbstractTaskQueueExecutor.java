package net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor;

import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jueb.util4j.queue.taskQueue.TaskConvert;
import net.jueb.util4j.queue.taskQueue.TaskQueue;
import net.jueb.util4j.queue.taskQueue.TaskQueueExecutor;
import net.jueb.util4j.queue.taskQueue.impl.DefaultTaskConvert;
import net.jueb.util4j.queue.taskQueue.impl.DefaultTaskQueue;

public abstract class AbstractTaskQueueExecutor implements TaskQueueExecutor{

	protected Logger log=LoggerFactory.getLogger(getClass());
	private final TaskQueue queue;
	public static final TaskConvert DEFAULT_TASK_CONVERT=new DefaultTaskConvert();
	private TaskConvert taskConvert=DEFAULT_TASK_CONVERT;
	
	public AbstractTaskQueueExecutor(String queueName) {
		this.queue=new DefaultTaskQueue(queueName);
	}
	
	public AbstractTaskQueueExecutor(TaskQueue queue) {
		if (queue == null)
            throw new NullPointerException();
		this.queue=queue;
	}
	
	@Override
	public final void execute(Runnable command) {
		execute(getTaskConvert().convert(command));
	}

	@Override
	public final String getQueueName() {
		return queue.getQueueName();
	}

	public final TaskQueue getQueue() {
		return queue;
	}
	
	@Override
	public TaskConvert getTaskConvert() {
		return taskConvert;
	}
	
	@Override
	public void setTaskConvert(TaskConvert taskConvert) {
		if(taskConvert==null)
		{
			throw new NullArgumentException("taskConvert is null");
		}
		this.taskConvert=taskConvert;
	}
}
