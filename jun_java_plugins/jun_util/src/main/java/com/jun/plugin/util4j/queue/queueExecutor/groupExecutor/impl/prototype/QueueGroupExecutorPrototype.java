package com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.impl.prototype;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.lock.waiteStrategy.SleepingWaitConditionStrategy;
import com.jun.plugin.util4j.lock.waiteStrategy.WaitCondition;
import com.jun.plugin.util4j.lock.waiteStrategy.WaitConditionStrategy;
import com.jun.plugin.util4j.queue.queueExecutor.RunnableQueue;
import com.jun.plugin.util4j.queue.queueExecutor.executor.QueueExecutor;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.QueueGroupExecutorBase;

/**
 * 队列组执行器设计原型 2016.10.14
 * @author juebanlin
 */
public final class QueueGroupExecutorPrototype implements QueueGroupExecutorBase{
    
	protected Logger log=LoggerFactory.getLogger(getClass());
	
    private static final int DEFAULT_INITIAL_THREAD_POOL_SIZE = 0;

    private static final int DEFAULT_MAX_THREAD_POOL = 8;

    private static final int DEFAULT_KEEP_ALIVE_SEC = 30;
    
	private volatile ThreadFactory threadFactory;
    
    /**
	 * 核心线程数，核心线程会一直存活，即使没有任务需要处理。
	 * 当线程数小于核心线程数时，即使现有的线程空闲，线程池也会优先创建新线程来处理任务，而不是直接交给现有的线程处理。 
	 * 核心线程在allowCoreThreadTimeout被设置为true时会超时退出，默认情况下不会退出。
	 */
	private volatile int corePoolSize;
	/**
	 *当线程数大于或等于核心线程，且任务队列已满时，线程池会创建新的线程，直到线程数量达到maxPoolSize。
	 *如果线程数已等于maxPoolSize，且任务队列已满，则已超出线程池的处理能力，线程池会拒绝处理任务而抛出异常。
	 */
    private volatile int maximumPoolSize;
    /**
     * 默认都是0纳秒，当线程没有任务处理后，保持多长时间，cachedPoolSize是默认60s，不推荐使用。
     */
    private volatile long keepAliveNanoTime;
    /**
     * 是否允许核心线程空闲退出，默认值为false。
     */
    private volatile boolean allowCoreThreadTimeOut;
    /**
     * 记录了线程池在整个生命周期中曾经出现的最大线程个数。
     */
    private volatile int largestPoolSize;
    
    /**
     * 最大队列插槽数
     */
    public static final int MAX_SOLT_COUNT = 0xFFFF;
    
    /**
     * 队列插槽
     */
    private final SoltQueue[] solts=new SoltQueue[MAX_SOLT_COUNT+1];
    
    /**
     * 队列别名
     */
    private final String[] soltAlias=new String[solts.length];
    
    /**
     * 系统队列
     */
    private final EventQueue systemQueue = new EventQueue("systemQueue");
    
    /**
     * 队列处理线程
     */
    private final Set<Worker> workers = new HashSet<Worker>();

    /**
     * 活动线程数量
     */
    private final AtomicInteger idleWorkers = new AtomicInteger();

    private volatile boolean shutdown;

    private final WaitConditionStrategy waitConditionStrategy;
    
    
    protected void init()
    {
    	for(int i=0;i<solts.length;i++)
    	{
    		solts[i]=new SoltQueue(i);
    	}
    }
    
    public QueueGroupExecutorPrototype() {
        this(DEFAULT_INITIAL_THREAD_POOL_SIZE, DEFAULT_MAX_THREAD_POOL);
    }

    public QueueGroupExecutorPrototype(int corePoolSize, int maximumPoolSize) {
        this(corePoolSize, maximumPoolSize,new SleepingWaitConditionStrategy());
    }
    
    public QueueGroupExecutorPrototype(int corePoolSize, int maximumPoolSize,
    		WaitConditionStrategy waitConditionStrategy) {
        this(corePoolSize, maximumPoolSize, DEFAULT_KEEP_ALIVE_SEC, TimeUnit.SECONDS, Executors.defaultThreadFactory(),waitConditionStrategy);
    }
    
    public QueueGroupExecutorPrototype(int corePoolSize, int maximumPoolSize, 
    		long keepAliveTime, TimeUnit unit,
            ThreadFactory threadFactory,
            WaitConditionStrategy waitConditionStrategy) {
		if (corePoolSize < 0 
				||maximumPoolSize <= 0 
				||maximumPoolSize < corePoolSize 
				||keepAliveTime < 0
				||threadFactory==null  
				||waitConditionStrategy==null)
		{
			throw new IllegalArgumentException();
		}
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveNanoTime = unit.toNanos(keepAliveTime);
		this.threadFactory=threadFactory;
        this.waitConditionStrategy=waitConditionStrategy;
        init();
    }
	
	public final ThreadFactory getThreadFactory() {
		return threadFactory;
	}

	public final void setThreadFactory(ThreadFactory threadFactory) {
		if(threadFactory==null)
		{
			throw new IllegalArgumentException("threadFactory is null");
		}
		this.threadFactory = threadFactory;
	}
    
    public long getKeepAliveTime(TimeUnit unit) {
    	return unit.convert(keepAliveNanoTime,TimeUnit.NANOSECONDS);
	}

	public void setKeepAliveTime(long keepAliveTime,TimeUnit unit) {
		if (keepAliveTime < 0 || unit==null )
		{
			throw new IllegalArgumentException();
		}
		this.keepAliveNanoTime = unit.toNanos(keepAliveTime);
	}

	public boolean isAllowCoreThreadTimeOut() {
		return allowCoreThreadTimeOut;
	}

	public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
		this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
	    if (corePoolSize < 0) {
	        throw new IllegalArgumentException("corePoolSize: " + corePoolSize);
	    }
	    if (corePoolSize > getMaximumPoolSize()) {
	        throw new IllegalArgumentException("corePoolSize exceeds maximumPoolSize");
	    }
	    synchronized (workers) {
	        if (getCorePoolSize() > corePoolSize) {
	            for (int i = getCorePoolSize() - corePoolSize; i > 0; i--) {
	                removeWorker();
	            }
	        }
	        setCorePoolSize(corePoolSize);
	    }
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
	    if ((maximumPoolSize <= 0) || (maximumPoolSize < getCorePoolSize())) {
	        throw new IllegalArgumentException("maximumPoolSize: " + maximumPoolSize);
	    }
	    synchronized (workers) {
	        setMaximumPoolSize(maximumPoolSize);
	        int difference = workers.size() - maximumPoolSize;
	        while (difference > 0) {
	            removeWorker();
	            --difference;
	        }
	    }
	}

	public int getPoolSize() {
	    synchronized (workers) {
	        return workers.size();
	    }
	}

	public int getActiveCount() {
	    synchronized (workers) {
	        return workers.size() - idleWorkers.get();
	    }
	}

	public int getLargestPoolSize() {
		return largestPoolSize;
	}
	
	protected void setLargestPoolSize(int size)
	{
		this.largestPoolSize=size;
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
	        	systemExecute(exitTask);
	        }
	    }
	}

	/**
	 * 同步关闭
	 */
	public void shutdownSync() {
		 shutdown();
	     for(;;)
	     {
	    	 if(workers.isEmpty())
	    	 {
	    		 break;
	    	 }
	    	 systemExecute(exitTask);
	         Thread.yield(); // Let others take the signal.
	         continue;
	     }
	}

	public boolean isTerminating() {
	    synchronized (workers) {
	        return isShutdown() && !isTerminated();
	    }
	}

	private void addWorkerUnsafe() {
         Worker worker = new Worker();
         Thread thread = getThreadFactory().newThread(worker);
         idleWorkers.incrementAndGet();
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
            	if (workers.size() >=getMaximumPoolSize()) {
                    return;
                }
                if (workers.isEmpty() || (idleWorkers.get() == 0)) {
                    addWorkerUnsafe();
                }
            }
        }
    }

    private void removeWorker() {
        synchronized (workers) {
            if (workers.size() <= getCorePoolSize()) {
                return;
            }
            systemExecute(exitTask);
        }
    }

    /**
     * 插槽队列处理任务
     * @author juebanlin
     */
    final Runnable exitTask=new WorkerExitTask() {
		@Override
		public void run() {
			log.info("线程退出信号执行");
		}
	};
	
	protected interface WorkerExitTask extends Runnable{
		
	}
    
    /**
     * 线程处理逻辑
     * @author juebanlin
     */
    private class Worker implements Runnable {
		public void run() {
            long lastRunTaskTime=System.currentTimeMillis();
            try {
                for (;;) 
                {
                	//徘徊获取一个任务
                	Runnable task = loopFetchTask();
                	if(task==null)
                	{//如果没有任务
                		long freeTime=System.currentTimeMillis()-lastRunTaskTime;//空闲时间
                		if(freeTime>getKeepAliveTime(TimeUnit.MILLISECONDS))
                		{//空闲时间达标,如果不是核心线程则退出
                			synchronized (workers) 
                            {
                                if (workers.size() > getCorePoolSize()) 
                                {
                                    workers.remove(this);
                                    log.debug("核心线程数超标,空闲线程退出");
                                    break;//退出线程
                                }
                            }
                		}
                		continue;//继续寻找任务
                	}
					idleWorkers.decrementAndGet();//活动线程-1
					try {
						addWorkerIfNecessary();//预备一个线程,如果有新任务则可立马执行
						lastRunTaskTime=System.currentTimeMillis();
						task.run();
					} finally {
						//不管异常与否都+1,如果有异常导致退出循环,则循环外会活动线程-1
						idleWorkers.incrementAndGet();//活动线程+1
						if(task instanceof WorkerExitTask) 
						{//如果是退出任务则退出,不管执行是否异常
							log.debug("退出线程,from WorkerExitTask:"+task);
							break;
						}
					}
                }
            } finally {
                synchronized (workers) {
                    workers.remove(this);
                    workers.notifyAll();
                    idleWorkers.decrementAndGet();//异常或者正常退出都会活动线程-1
                }
            }
        }
        
		/**
		 * 查找任务
		 * @return
		 */
		private Runnable findTask()
		{
        	//执行系统任务
        	return systemQueue.poll();
		}
		
        /**
         * 循环获取任务
         * @return
         */
        private Runnable loopFetchTask()
        {
        	Runnable task = null;
            long currentTime = System.currentTimeMillis();
            long maxFreeTime=getKeepAliveTime(TimeUnit.MILLISECONDS);//最大徘徊时间
            long deadline = currentTime +maxFreeTime; //退出获取的时间点
            for (;;) {
                try {
                    long waitTime = deadline - currentTime;
                    if (waitTime <= 0) 
                    {
                        break;
                    }
                    try {
                    	long t=System.currentTimeMillis();
                    	task = waitingTask(waitTime, TimeUnit.MILLISECONDS);//取出待执行的队列,阻塞waitTime
                    	t=System.currentTimeMillis()-t;
                    	if(task==null)
                    	{
                    		log.trace("空闲等待时间结束,实际等待时间"+t+"/"+waitTime+",task="+task);
                    	}
                        if(task!=null)
                        { //TODO 如果waitingTask方法没有达到最长等待时间,却返回了null,则循环继续;waitTime会计算实际超时时间并退出循环
                        	break;
                        }
                    } 
                    finally {
                        if (task == null) {
                            currentTime = System.currentTimeMillis();
                        }
                    }
                } catch (Exception e) {
                	log.error(e.getMessage(),e);
                    // Ignore.
                    continue;
                }
            }
            return task;
        }
        
        /**
         * 等待获取一个任务
         * @param waiteTime
         * @param unit
         * @return
         * @throws InterruptedException
         */
        private Runnable waitingTask(long waiteTime,TimeUnit unit) throws InterruptedException
        {
        	workerWaitCondition.init(waiteTime, unit);//初始化条件参数
        	//等待一个条件通过
        	return waitConditionStrategy.waitFor(workerWaitCondition,waiteTime,unit);
        }
        
        WorkerWaitCondition workerWaitCondition=new WorkerWaitCondition();
        /**
         * 等待条件
         * @author juebanlin
         */
        private class WorkerWaitCondition implements WaitCondition<Runnable>
        {
        	private volatile Runnable task;
        	private volatile long  endTime;
        	
        	public void init(long waiteTime,TimeUnit unit)
        	{
        		task=null;
        		endTime=System.nanoTime()+unit.toNanos(waiteTime);
        	}
        	
        	@Override
			public Runnable getAttach() {
				return task;
			}

			@Override
			public boolean isComplete() {//验证条件是否通过
				if(task!=null || System.nanoTime()>=endTime)
				{
					return true;
				}
				task= findTask();
				return task!=null;
			}
        }
    }
    
    /**
     * 基于事件的队列
     * @author juebanlin
     */
    private class EventQueue extends ConcurrentLinkedQueue<Runnable> implements RunnableQueue,QueueExecutor{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2961878968488809736L;

		private final String name;
		public EventQueue() {
			this.name="EventQueue";
		}
		public EventQueue(String name) {
			this.name=name;
		}
		
		@Override
		public String getQueueName() {
			return name;
		}

		/**
		 * 有任务进入
		 */
		protected void onOfferd()
		{
			
		}
		
		@Override
        public final boolean offer(Runnable e) {
			event_taskOfferBefore(this);
        	boolean bool=super.offer(e);
        	onOfferd();
        	event_taskOfferAfter(this,bool);
        	return bool;
        }

		@Override
		public final boolean addAll(Collection<? extends Runnable> c) {
			event_taskOfferBefore(this);
        	boolean bool=super.addAll(c);
        	onOfferd();
        	event_taskOfferAfter(this,bool);
        	return bool;
		}
		
		@Override
		public final void execute(Runnable task) {
			offer(task);
		}
		
		@Override
		public final void execute(List<Runnable> tasks) {
			addAll(tasks);
		}
    }
    
    /**
     * 插槽队列
     * @author juebanlin
     */
    private class SoltQueue extends EventQueue{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1711281918590904219L;
		private final int soltIndex;
		/**
         * 此队列是否锁定/是否被线程占用
         * 次属性仅供本类持有
         */
        private final AtomicBoolean isLock = new AtomicBoolean(false);
        /**
         * 此队列完成的任务数量
         */
		private final AtomicLong completedTaskCount = new AtomicLong(0);
		
		
		@Override
		public String getQueueName() {
			return getAlias((short) soltIndex);
		}
		
		public SoltQueue(int soltIndex) {
			this.soltIndex=soltIndex;
			init();
		}
		
		/**
		 * 初始化状态
		 */
		public void init(){
			isLock.set(false);
			completedTaskCount.set(0);
			super.clear();
		}
		
		public AtomicLong getCompletedTaskCount() {
			return completedTaskCount;
		}
		
		/**
		 * 当有任务添加时,将这些任务处理逻辑交给系统
		 */
		@Override
		protected void onOfferd() {
			super.onOfferd();
			if(isLock.compareAndSet(false, true))
   		 	{
				systemExecute(new SoltQueueProcessTask(this));
   		 	}
		}
		
		private class SoltQueueProcessTask implements Runnable{
	    	SoltQueue queue;
	    	public SoltQueueProcessTask(SoltQueue queue) {
	    		this.queue=queue;
			}
			@Override
			public void run() {
				try {
					handleQueueTask(queue);
				} finally {
					queue.isLock.set(false);
				}
			}
			
			 /**
	         * 处理队列任务
	         * @param queue
	         */
	        private void handleQueueTask(SoltQueue queue) {
	        	Thread thread=Thread.currentThread();
	        	for (;;) 
	            {
	        		Runnable task = queue.poll();
	            	if(task == null)
	                {//停止处理队列
	            		break;
	                }
	            	beforeExecute(thread, task);
	                boolean succeed = false;
	                try {
	                    task.run();
	                    queue.getCompletedTaskCount().incrementAndGet();
	                    succeed = true;
	                    afterExecute(task, null);
	                } catch (RuntimeException e) {
	                    if (!succeed) {
	                        afterExecute(task, e);
	                    }
	                    throw e;
	                }
	            }
	        }
	    }
    }
    
	/**
     * 队列添加事件执行之前
     * @param queue
     */
    protected void event_taskOfferBefore(EventQueue queue)
    {//如果关机则可以抛出异常
    	
    }
    
    /**
     * 队列添加事件执行之后
     * @param queue
     * @param offered
     */
    protected void event_taskOfferAfter(EventQueue queue,boolean offered)
    {
	    addWorkerIfNecessary();
	    waitConditionStrategy.signalAllWhenBlocking();//如果有线程阻塞等待,则释放阻塞去处理任务
    }
    
    protected void afterExecute(Runnable task,Exception object) {
		
	}
    protected void beforeExecute(Thread thread, Runnable task) {
		
	}
    
    /**
     * 转换为插槽索引
     * @param solt
     * @return
     */
    protected final int convertIndex(short solt)
	{
		byte a=(byte)(solt>>8&0xFF);//高8位
		byte b=(byte)(solt&0xFF);//低8位
		int value=((int)(a))<<8|(int)(b);
		return value & 0xffff;
	}
    
	public long getCompletedTaskCount() {
		long v=0;
	    for(SoltQueue sq:solts)
	    {
	    	v+=sq.getCompletedTaskCount().get();
	    }
	    return v;
	}

	public Iterator<QueueExecutor> iterator() {
		return new Iterator<QueueExecutor>() {
			int i=0;
			@Override
			public boolean hasNext() {
				return i<solts.length;
			}

			@Override
			public QueueExecutor next() {
				return solts[i++];
			}
		};
	}
	
	@Override
	public void execute(short solt, Runnable task) {
		if(task ==null)
    	{
    		throw new RuntimeException("task is null");
    	}
		getQueueExecutor(solt).execute(task);
	}

	@Override
	public void execute(short solt, List<Runnable> tasks) {
		if(tasks ==null)
    	{
    		throw new RuntimeException("tasks is null");
    	}
		getQueueExecutor(solt).execute(tasks);
	}

	@Override
	public void setAlias(short solt, String alias) {
		soltAlias[convertIndex(solt)]=alias;
	}

	@Override
	public String getAlias(short solt) {
		return soltAlias[convertIndex(solt)];
	}

	@Override
	public QueueExecutor getQueueExecutor(short solt) {
		int index=convertIndex(solt);
		return solts[index];
	}
	
	protected void systemExecute(Runnable task)
	{
		if(task ==null)
    	{
    		throw new RuntimeException("task is null");
    	}
		systemQueue.execute(task);
	}
	
	protected void systemExecute(List<Runnable> tasks)
	{
		if(tasks ==null)
    	{
    		throw new RuntimeException("tasks is null");
    	}
		systemQueue.execute(tasks);
	}

	@Override
	public Iterator<QueueExecutor> indexIterator() {
		return iterator();
	}
}
