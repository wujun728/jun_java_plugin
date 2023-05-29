package net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.singleThread;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import net.jueb.util4j.queue.taskQueue.Task;
import net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.AbstractTaskQueueExecutor;

/**
 * 单线程任务队列执行器
 * 采用半阻塞队列驱动唤醒线程
 * @author juebanlin
 */
public class SingleThreadTaskQueueExecutor extends AbstractTaskQueueExecutor{

	private final ThreadFactory threadFactory;
	
	public SingleThreadTaskQueueExecutor(String queueName) {
		this(queueName,Executors.defaultThreadFactory());
	}
	
	public SingleThreadTaskQueueExecutor(String queueName,ThreadFactory threadFactory) {
		super(queueName);
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
	private final ReentrantLock lock=new ReentrantLock();
	private final Condition notEmpty=lock.newCondition();
	
	protected void update()
	{
		addWorkerIfNecessary();
		signalNotEmpty();
	}
	
	/**
	 * 通知线程不为空
	 */
	protected void signalNotEmpty(){
		lock.lock();
		try {
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 等待不为空
	 */
	protected void waitNotEmpty(long time,TimeUnit unit)
	{
		lock.lock();
		try {
			notEmpty.await(time,unit);
		} catch (InterruptedException e) {
		} finally {
			lock.unlock();
		}
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
            	for(;;)
            	{
            		Task task=getQueue().poll();
            		if(task==null)
            		{
            			waitNotEmpty(100,TimeUnit.MILLISECONDS);
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
