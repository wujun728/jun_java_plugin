package net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jueb.util4j.queue.taskQueue.Task;
import net.jueb.util4j.queue.taskQueue.TaskConvert;
import net.jueb.util4j.queue.taskQueue.TaskQueuesExecutor;
import net.jueb.util4j.queue.taskQueue.impl.DefaultTaskConvert;

public abstract class AbstractTaskQueuesExecutor extends AbstractExecutorService implements TaskQueuesExecutor{

	protected Logger log=LoggerFactory.getLogger(getClass());
	public static final TaskConvert DEFAULT_TASK_CONVERT=new DefaultTaskConvert();
	private TaskConvert taskConvert=DEFAULT_TASK_CONVERT;
	private volatile ThreadFactory threadFactory;
	public static final String DEFAULT_QUEUE="DEFAULT_QUEUE";
	
	public AbstractTaskQueuesExecutor() {
		this(Executors.defaultThreadFactory());
	}
	
	public AbstractTaskQueuesExecutor(ThreadFactory threadFactory) {
		this.threadFactory=threadFactory;
	}
	
	public final ThreadFactory getThreadFactory() {
		return threadFactory;
	}

	public final void setThreadFactory(ThreadFactory threadFactory) {
		if(threadFactory==null)
		{
			throw new NullArgumentException("threadFactory is null");
		}
		this.threadFactory = threadFactory;
	}
	
	/**
	 * 默认队列名,用于存放非Task类型
	 * @return
	 */
	public String getDefaultQueueName()
	{
		return DEFAULT_QUEUE;
	}
	
	@Override
	public final void execute(Runnable command) {
		if(command==null)
		{
			throw new NullArgumentException("command is null");
		}
		if(command instanceof Task)
		{
			execute(getDefaultQueueName(),(Task)command);
		}else
		{
			execute(getDefaultQueueName(),getTaskConvert().convert(command));
		}
	}
	
	@Override
	public final void execute(String queueName, Runnable task) {
		if(queueName==null || task==null)
		{
			throw new NullArgumentException("arg is null");
		}
		if(task instanceof Task)
		{
			execute(queueName,(Task)task);
		}else
		{
			execute(queueName,getTaskConvert().convert(task));
		}
	}
	
	@Override
	public final TaskConvert getTaskConvert() {
		return taskConvert;
	}

	@Override
	public final void setTaskConvert(TaskConvert taskConvert) {
		if(taskConvert==null)
		{
			throw new NullArgumentException("taskConvert is null");
		}
		this.taskConvert=taskConvert;
	}
}
