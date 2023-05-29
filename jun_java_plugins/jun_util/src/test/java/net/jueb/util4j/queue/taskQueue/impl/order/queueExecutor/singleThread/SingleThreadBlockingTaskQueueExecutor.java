package net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.singleThread;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import net.jueb.util4j.queue.taskQueue.Task;
import net.jueb.util4j.queue.taskQueue.impl.BlockingTaskQueue;
import net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.AbstractTaskQueueExecutor;

/**
 * 单线程任务队列执行器
 * 采用阻塞队列驱动唤醒线程
 * 效率较低,但安全
 * @author juebanlin
 */
public class SingleThreadBlockingTaskQueueExecutor extends AbstractTaskQueueExecutor{

	private final ThreadFactory threadFactory;
	
	public SingleThreadBlockingTaskQueueExecutor(String queueName) {
		this(queueName,Executors.defaultThreadFactory());
	}
	
	public SingleThreadBlockingTaskQueueExecutor(String queueName,ThreadFactory threadFactory) {
		super(new BlockingTaskQueue(queueName));
		if (threadFactory == null)
	            throw new NullPointerException();
		this.threadFactory = threadFactory;
	}
	
	public ThreadFactory getThreadFactory() {
		return threadFactory;
	}

	@Override
	public void execute(Task task) {
		getQueue().offer(task);
		update();
	}
	
	@Override
	public void execute(List<Task> tasks) {
		getQueue().addAll(tasks);
		update();
	}

	private final Set<Worker> workers = new HashSet<Worker>();
	
	protected void update()
	{
		addWorkerIfNecessary();
	}
	
	private void addWorkerIfNecessary() 
	{
		if(workers.isEmpty())
		{
			synchronized (workers) {
				if(workers.isEmpty())
				{
					addWorker();
				}
			}
		}
    }
	
	private void addWorker()
	{
		Worker worker = new Worker();
	    Thread thread = getThreadFactory().newThread(worker);
	    thread.start();
	    workers.add(worker);
	}

	private class Worker implements Runnable {
		
        public void run() {
            try {
            	BlockingTaskQueue queue=(BlockingTaskQueue) getQueue();
            	for(;;)
            	{
            		Task task=null;
					try {
						task =queue.poll(100,TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						
					}
					if(task==null)
					{
						continue;
					}
                    try {
                		runTask(task);
					} catch (Throwable e) {
						log.error(e.getMessage(),e);
					}
            	}
            } finally {
            	synchronized (workers) {
            		workers.remove(this);
                    workers.notifyAll();
				}
            }
        }
    }
	
	protected void runTask(Task task)
	{
		task.run();
	}
}
