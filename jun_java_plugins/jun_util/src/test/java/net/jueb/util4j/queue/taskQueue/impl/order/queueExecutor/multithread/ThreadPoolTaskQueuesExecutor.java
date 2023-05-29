package net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.multithread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.NullArgumentException;

import com.jun.plugin.util4j.lock.waiteStrategy.SleepingWaitConditionStrategy;
import com.jun.plugin.util4j.lock.waiteStrategy.WaitCondition;
import com.jun.plugin.util4j.lock.waiteStrategy.WaitConditionStrategy;

import net.jueb.util4j.queue.taskQueue.Task;
import net.jueb.util4j.queue.taskQueue.TaskConvert;
import net.jueb.util4j.queue.taskQueue.TaskQueue;
import net.jueb.util4j.queue.taskQueue.TaskQueueExecutor;
import net.jueb.util4j.queue.taskQueue.TaskQueuesExecutor;
import net.jueb.util4j.queue.taskQueue.impl.DefaultTaskQueue;
import net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.AbstractThreadPoolTaskQueuesExecutor;

public class ThreadPoolTaskQueuesExecutor extends AbstractThreadPoolTaskQueuesExecutor implements TaskQueuesExecutor{
    
    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = 0;

    private static final int DEFAULT_MAX_THREAD_POOL = 8;

    private static final int DEFAULT_KEEP_ALIVE = 30;

    private static final String QUEUE_NAME_EXIT_SIGNAL = "EXIT_SIGNAL";

    public static final String QUEUE_NAME_DEFAULT_QUEUE="DEFAULT_QUEUE";

    /**
     *等待处理的队列
     */
    private final Queue<String> waitingQueues = new ConcurrentLinkedQueue<String>();
    /**
     * 
     * 队列
     */
    private final Map<String,TaskQueueImpl> queues=new HashMap<String,TaskQueueImpl>();
    
    /**
     * 队列处理线程
     */
    private final Set<Worker> workers = new HashSet<Worker>();

    /**
     * 活动线程数量
     */
    private final AtomicInteger idleWorkers = new AtomicInteger();
    /**
     * 用来记录已经执行完毕的任务个数
     */
    private long completedTaskCount;

    private volatile boolean shutdown;

    private final WaitConditionStrategy waitConditionStrategy;
    
    public ThreadPoolTaskQueuesExecutor() {
        this(DEFAULT_INITIAL_THREAD_POOL_SIZE, DEFAULT_MAX_THREAD_POOL);
    }

    public ThreadPoolTaskQueuesExecutor(int corePoolSize, int maximumPoolSize) {
        this(corePoolSize, maximumPoolSize,new SleepingWaitConditionStrategy());
    }
    
    public ThreadPoolTaskQueuesExecutor(int corePoolSize, int maximumPoolSize,
    		WaitConditionStrategy waitConditionStrategy) {
        this(corePoolSize, maximumPoolSize, DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS, Executors.defaultThreadFactory(),waitConditionStrategy);
    }
    
    public ThreadPoolTaskQueuesExecutor(int corePoolSize, int maximumPoolSize, 
    		long keepAliveTime, TimeUnit unit,
            ThreadFactory threadFactory,
            WaitConditionStrategy waitConditionStrategy) {
        super(corePoolSize,maximumPoolSize, keepAliveTime, unit,threadFactory);
        if(waitConditionStrategy==null)
        {
        	 throw new IllegalArgumentException("waitConditionStrategy: " + waitConditionStrategy);
        }
        // Now, we can setup the pool sizes
        this.waitConditionStrategy=waitConditionStrategy;
    }
    
    protected String waitingQueuePoll()
    {
    	return waitingQueues.poll();
    }
    
    protected boolean waitingQueueOffer(String queueName)
    {
    	boolean bool=waitingQueues.offer(queueName);
    	waitConditionStrategy.signalAllWhenBlocking();
    	return bool;
    }
    
    /**
     * 取队列
     * @param queueName
     * @return
     */
    private TaskQueueImpl getTaskQueue(String queueName) {
        return queues.get(queueName);
    }

    /**
     * Add a new thread to execute a task, if needed and possible.
     * It depends on the current pool size. If it's full, we do nothing.
     */
    private void addWorkerUnsafe() {
    	 if (workers.size() >= super.getMaximumPoolSize()) {
             return;
         }

         // Create a new worker, and add it to the thread pool
         Worker worker = new Worker();
         Thread thread = getThreadFactory().newThread(worker);

         // As we have added a new thread, it's considered as idle.
         idleWorkers.incrementAndGet();

         // Now, we can start it.
         thread.start();
         workers.add(worker);
         if (workers.size() > getLargestPoolSize()) {
             setLargestPoolSize(workers.size());
         }
    }

    /**
     * 如果活动的线程数量=0则添加线程
     */
    private void addWorkerIfNecessary() {
        if (idleWorkers.get() == 0) {
            synchronized (workers) {
                if (workers.isEmpty() || (idleWorkers.get() == 0)) {
                    addWorkerUnsafe();
                }
            }
        }
    }

    private void removeWorker() {
        synchronized (workers) {
            if (workers.size() <= super.getCorePoolSize()) {
                return;
            }
            waitingQueueOffer(QUEUE_NAME_EXIT_SIGNAL);
        }
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        if ((maximumPoolSize <= 0) || (maximumPoolSize < getCorePoolSize())) {
            throw new IllegalArgumentException("maximumPoolSize: " + maximumPoolSize);
        }
        synchronized (workers) {
            super.setMaximumPoolSize(maximumPoolSize);
            int difference = workers.size() - maximumPoolSize;
            while (difference > 0) {
                removeWorker();
                --difference;
            }
        }
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long deadline = System.currentTimeMillis() + unit.toMillis(timeout);
        synchronized (workers) {
            while (!isTerminated()) {
                long waitTime = deadline - System.currentTimeMillis();
                if (waitTime <= 0) {
                    break;
                }
                workers.wait(waitTime);
            }
        }
        return isTerminated();
    }

    public boolean isShutdown() {
        return shutdown;
    }

    public boolean isTerminated() {
        if (!shutdown) {
            return false;
        }
        synchronized (workers) {
            return workers.isEmpty();
        }
    }

    public void shutdown() {
        if (shutdown) 
        {
            return;
        }
        shutdown = true;
        synchronized (workers) 
        {
            for (int i = workers.size(); i > 0; i--) 
            {
            	waitingQueueOffer(QUEUE_NAME_EXIT_SIGNAL);
            }
        }
    }

    public List<Runnable> shutdownNow() {
        shutdown();
        List<Runnable> answer = new ArrayList<Runnable>();
        for(;;)
        {
        	 String queueName = waitingQueuePoll();
        	 if(queueName==null)
        	 {
        		 break;
        	 }
        	 if (queueName == QUEUE_NAME_EXIT_SIGNAL) 
        	 {
             	waitingQueueOffer(QUEUE_NAME_EXIT_SIGNAL);
                 Thread.yield(); // Let others take the signal.
                 continue;
             }
             TaskQueue tasksQueue = closeQueue(queueName);
             if(tasksQueue!=null)
             {
             	 answer.addAll(tasksQueue);
                 tasksQueue.clear();
             }
        }
        return answer;
    }

	@Override
	public void execute(String queueName, Task task) {
    	if(queueName==null || task ==null)
    	{
    		throw new NullArgumentException("queueName is null");
    	}
    	if(shutdown)
    	{
    		rejectTask(task);
    	}
    	TaskQueueImpl tasksQueue = getTaskQueue(queueName);
    	if(tasksQueue!=null)
    	{
    		tasksQueue.offer(task);
    	}
	}
    
    @Override
	public void execute(String queueName,List<Task> tasks) {
		if(queueName==null || tasks ==null)
    	{
    		throw new NullArgumentException("queueName is null");
    	}
		if(shutdown)
		{
			for(Task t:tasks)
			{
				rejectTask(t);
			}
		}
    	TaskQueueImpl tasksQueue = getTaskQueue(queueName);
    	if(tasksQueue!=null)
    	{
    		tasksQueue.addAll(tasks);
    	}
	}

	/**
     * 队列添加事件
     * @param queue
     */
    protected void queueOfferEvent(TaskQueueImpl tasksQueue)
    {
    	 if (tasksQueue.processingCompleted.compareAndSet(true, false)) 
         {//如果该队列没有线程占用则放入公共任务队列,如果有线程占用,则不放入,因为不允许其它线程同时访问此队列
         	waitingQueueOffer(tasksQueue.getQueueName());
         }
         addWorkerIfNecessary();
    }
    

	@Override
	public TaskQueueExecutor getQueueExecutor(String queueName) {
		return getTaskQueue(queueName);
	}
	
	@Override
	public TaskQueueExecutor openQueue(String queueName) {
		TaskQueueImpl queue = queues.get(queueName);
        if (queue == null) 
        {
           synchronized (queues) {
        	   queue = queues.get(queueName);
               if (queue == null) 
               {
                   queue = new TaskQueueImpl(queueName);
                   queues.put(queueName, queue);
               }
           }
        }
        return queue;
	}

	@Override
	public TaskQueue closeQueue(String queueName) {
		TaskQueueImpl queue=queues.remove(queueName);
		if(queue!=null)
		{
			queue.setOpen(false);
		}
		return queue;
	}

	private void rejectTask(Runnable task) {
        getRejectedExecutionHandler().rejectedExecution(task, this);
    }

    public int getActiveCount() {
        synchronized (workers) {
            return workers.size() - idleWorkers.get();
        }
    }

    public long getCompletedTaskCount() {
        synchronized (workers) {
            long answer = completedTaskCount;
            for (Worker w : workers) {
                answer += w.completedTaskCount.get();
            }
            return answer;
        }
    }

    public int getPoolSize() {
        synchronized (workers) {
            return workers.size();
        }
    }

    public boolean isTerminating() {
        synchronized (workers) {
            return isShutdown() && !isTerminated();
        }
    }

    public int prestartAllCoreThreads() {
        int answer = 0;
        synchronized (workers) {
            for (int i = super.getCorePoolSize() - workers.size(); i > 0; i--) {
                addWorkerUnsafe();
                answer++;
            }
        }
        return answer;
    }

    public boolean prestartCoreThread() {
        synchronized (workers) {
            if (workers.size() < getCorePoolSize()) {
                addWorkerUnsafe();
                return true;
            } else {
                return false;
            }
        }
    }

    public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize < 0) {
            throw new IllegalArgumentException("corePoolSize: " + corePoolSize);
        }
        if (corePoolSize > super.getMaximumPoolSize()) {
            throw new IllegalArgumentException("corePoolSize exceeds maximumPoolSize");
        }
        synchronized (workers) {
            if (super.getCorePoolSize() > corePoolSize) {
                for (int i = super.getCorePoolSize() - corePoolSize; i > 0; i--) {
                    removeWorker();
                }
            }
            super.setCorePoolSize(corePoolSize);
        }
    }
    
    private class Worker implements Runnable {

        private AtomicLong completedTaskCount = new AtomicLong(0);

        private Thread thread;

        public void run() {
            thread = Thread.currentThread();
            try {
                for (;;) 
                {
                	//获取一个队列
                	String queueName = fetchQueueName();//最长等待keepAlivetime
                	idleWorkers.decrementAndGet();//活动线程-1,如果还有队列没有人处理则会增加线程
					if(queueName==null)
					{//判断是否释放线程
						synchronized (workers) 
                        {
                            if (workers.size() > getCorePoolSize()) 
                            {
                                workers.remove(this);
                                break;//退出线程
                            }
                        }
					}
                    if (queueName == QUEUE_NAME_EXIT_SIGNAL) 
                    {
                        break;
                    }
                    TaskQueueImpl queue=null;
                    try {
                    	if(queueName!=null)
                        {
                    		queue=getTaskQueue(queueName);
                        }
                        if (queue != null) 
                        {
                            handleQueueTask(queue);
                        }
                    } finally {
                        if(queue!=null)
                        {//这里一定要设置队列状态,如果任务异常会导致其它线程获取不到该队列的处理权
                        	queue.processingCompleted.set(true);
                        }
                    }
                    //正常处理完任务后,归为活跃线程(异常会放弃回归)
                    idleWorkers.incrementAndGet();
                }
            } finally {
                synchronized (workers) {
                    workers.remove(this);
                    ThreadPoolTaskQueuesExecutor.this.completedTaskCount += completedTaskCount.get();
                    workers.notifyAll();
                }
            }
        }
        
        /**
         * 取出一个队列
         * 此方法阻塞
         * @return
         */
        final private String fetchQueueName() {
        	String queueName = null;
            long currentTime = System.currentTimeMillis();
            long deadline = currentTime + getKeepAliveTime(TimeUnit.MILLISECONDS);
            for (;;) {
                try {
                    long waitTime = deadline - currentTime;
                    if (waitTime <= 0) 
                    {
                        break;
                    }
                    try {
                    	//取出待执行的队列,阻塞waitTime
                        queueName = waitingQueuesPoll(waitTime, TimeUnit.MILLISECONDS);
                        if(queueName!=null)
                        { //TODO 如果waitingQueuesPoll方法没有达到最长等待时间,却返回了null,则循环继续;waitTime会计算实际超时时间并退出循环
                        	break;
                        }
                    } finally {
                        if (queueName == null) {
                            currentTime = System.currentTimeMillis();
                        }
                    }
                } catch (Exception e) {
                    // Ignore.
                    continue;
                }
            }
            return queueName;
        }
        
        WorkerWaitCondition workerWaitCondition=new WorkerWaitCondition();
        private class WorkerWaitCondition implements WaitCondition<String>
        {
        	private volatile String queueName;
        	private volatile long  endTime;
        	
        	public void init(long waiteTime,TimeUnit unit)
        	{
        		queueName=null;
        		endTime=System.nanoTime()+unit.toNanos(waiteTime);
        	}
        	
        	@Override
			public String getAttach() {
				return queueName;
			}

			@Override
			public boolean isComplete() {
				if(queueName==null)
				{
					queueName=waitingQueuePoll();
				}
				return queueName!=null ||System.nanoTime()>=endTime;
			}
        }
       
        private String waitingQueuesPoll(long waiteTime,TimeUnit unit) throws InterruptedException
        {
        	workerWaitCondition.init(waiteTime, unit);
        	return waitConditionStrategy.waitFor(workerWaitCondition,waiteTime,unit);
        }
        
        /**
         * 处理队列任务
         * @param queue
         */
        private void handleQueueTask(TaskQueueImpl queue) {
        	for (;;) 
            {
        		Runnable task = queue.poll();
            	if(!queue.isOpen() || task == null)
                {
            		break;//停止处理队列
                }
                runTask(task);
            }
        }

        private void runTask(Runnable task) {
            beforeExecute(thread, task);
            boolean ran = false;
            try {
                task.run();
                ran = true;
                afterExecute(task, null);
                completedTaskCount.incrementAndGet();
            } catch (RuntimeException e) {
                if (!ran) {
                    afterExecute(task, e);
                }
                throw e;
            }
        }
    }

    private class TaskQueueImpl extends DefaultTaskQueue implements TaskQueueExecutor{
        /**
		 * 
		 */
		private static final long serialVersionUID = -741373262667864219L;
		private volatile boolean isOpen=true;
		public TaskQueueImpl(String name) {
			super(name);
		}
		/**The current task state 
         * 此队列是否处理完成
         */
        private volatile AtomicBoolean processingCompleted = new AtomicBoolean(true);
        
		public boolean isOpen() {
			return isOpen;
		}

		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}
		
		@Override
        public final boolean offer(Task e) {
        	boolean bool=super.offer(e);
        	if(isOpen())
			{//还在当前队列执行器
				queueOfferEvent(this);
			}
        	return bool;
        }

		@Override
		public boolean addAll(Collection<? extends Task> c) {
			boolean bool=super.addAll(c);
			if(isOpen())
			{//还在当前队列执行器
				queueOfferEvent(this);
			}
        	return bool;
		}
        
		@Override
		public void execute(Runnable command) {
			offer(getTaskConvert().convert(command));
		}
		@Override
		public void execute(Task task) {
			offer(task);
		}
		@Override
		public void execute(List<Task> tasks) {
			addAll(tasks);
		}

		@Override
		public TaskConvert getTaskConvert() {
			return ThreadPoolTaskQueuesExecutor.this.getTaskConvert();
		}

		@Override
		public void setTaskConvert(TaskConvert taskConvert) {
			ThreadPoolTaskQueuesExecutor.this.setTaskConvert(taskConvert);
		}
    }
}
