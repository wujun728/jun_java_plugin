package net.jueb.util4j.queue.taskQueue.impl.order.orderTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jueb.util4j.queue.taskQueue.Task;
import net.jueb.util4j.queue.taskQueue.TaskConvert;
import net.jueb.util4j.queue.taskQueue.TaskQueue;
import net.jueb.util4j.queue.taskQueue.TaskQueueExecutor;
import net.jueb.util4j.queue.taskQueue.TaskQueuesExecutor;
import net.jueb.util4j.queue.taskQueue.impl.DefaultTaskConvert;
import net.jueb.util4j.queue.taskQueue.impl.DefaultTaskQueue;

public class OrderTaskQueueGroup implements TaskQueuesExecutor{
	public final Logger log = LoggerFactory.getLogger(getClass());
	private final ReentrantLock lock=new ReentrantLock();
	protected final Map<String,OrderTaskQueue> queueMap = new ConcurrentHashMap<String,OrderTaskQueue>();

	public OrderTaskQueue get(String key)
	{
		return queueMap.get(key);
	}
	
	public OrderTaskQueue put(String key,Task task)
	{
		OrderTaskQueue queue=queueMap.get(key);
		if(queue==null || !queue.isActive())
		{
			try {
				lock.lock();
				queue=queueMap.get(key);
				if(queue==null)
				{//对null的处理
					queue= new OrderTaskQueue(key);
					queueMap.put(key,queue);
					queue.start();
				}else
				{//对离线的处理
					if(!queue.isActive())
					{
						log.warn("线程队列:"+key+",离线,未处理任务数:"+queue.getTasks().size()+",重建线程队列线程……");
						queue.stop();
						queue= new OrderTaskQueue(key,queue.getTasks());
						queueMap.put(key,queue);
						queue.start();
						log.warn("线程队列:"+key+"重建:"+queue.isActive()+",未处理任务数:"+queue.getTasks().size()+"");
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}finally{
				lock.unlock();
			}
		}
		queue.addTask(task);
		return queue;
	}
	
	public OrderTaskQueue put(String key,List<Task> tasks)
	{
		OrderTaskQueue queue=queueMap.get(key);
		if(queue==null || !queue.isActive())
		{
			try {
				lock.lock();
				queue=queueMap.get(key);
				if(queue==null)
				{//对null的处理
					queue= new OrderTaskQueue(key);
					queueMap.put(key,queue);
					queue.start();
				}else
				{//对离线的处理
					if(!queue.isActive())
					{
						log.warn("线程队列:"+key+",离线,未处理任务数:"+queue.getTasks().size()+",重建线程队列线程……");
						queue.stop();
						queue= new OrderTaskQueue(key,queue.getTasks());
						queueMap.put(key,queue);
						queue.start();
						log.warn("线程队列:"+key+"重建:"+queue.isActive()+",未处理任务数:"+queue.getTasks().size()+"");
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}finally{
				lock.unlock();
			}
		}
		queue.addTask(tasks);
		return queue;
	}
	
	public void stop()
	{
		try {
			lock.lock();
			for(String key:queueMap.keySet())
			{
				OrderTaskQueue queue=queueMap.get(key);
				queue.stop();
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally
		{
			lock.unlock();
		}
	}

	@Override
	public void execute(String queueName, Task task) {
		put(queueName, task);
	}

	@Override
	public void execute(String queueName, List<Task> tasks) {
		put(queueName, tasks);
	}

	@Override
	public TaskQueueExecutor getQueueExecutor(String queueName) {
		return queueMap.get(queueName);
	}

	@Override
	public TaskQueueExecutor openQueue(String queueName) {
		OrderTaskQueue queue=queueMap.get(queueName);
		if(queue==null || !queue.isActive())
		{
			try {
				lock.lock();
				queue=queueMap.get(queueName);
				if(queue==null)
				{//对null的处理
					queue= new OrderTaskQueue(queueName);
					queueMap.put(queueName,queue);
					queue.start();
				}else
				{//对离线的处理
					if(!queue.isActive())
					{
						log.warn("线程队列:"+queueName+",离线,未处理任务数:"+queue.getTasks().size()+",重建线程队列线程……");
						queue.stop();
						queue= new OrderTaskQueue(queueName,queue.getTasks());
						queueMap.put(queueName,queue);
						queue.start();
						log.warn("线程队列:"+queueName+"重建:"+queue.isActive()+",未处理任务数:"+queue.getTasks().size()+"");
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}finally{
				lock.unlock();
			}
		}
		return queue;
	}

	@Override
	public TaskQueue closeQueue(String queueName) {
		OrderTaskQueue queue=queueMap.remove(queueName);
		if(queue!=null)
		{
			queue.stop();
		}
		DefaultTaskQueue d=new DefaultTaskQueue(queueName);
		d.addAll(queue.getTasks());
		return d;
	}

	public static final TaskConvert DEFAULT_TASK_CONVERT=new DefaultTaskConvert();
	private TaskConvert taskConvert=DEFAULT_TASK_CONVERT;
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
}
