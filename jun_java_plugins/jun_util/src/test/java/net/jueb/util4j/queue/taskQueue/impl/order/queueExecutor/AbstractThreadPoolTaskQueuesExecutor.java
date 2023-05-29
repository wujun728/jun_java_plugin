package net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import net.jueb.util4j.queue.taskQueue.TaskQueuesExecutor;

public abstract class AbstractThreadPoolTaskQueuesExecutor extends AbstractTaskQueuesExecutor implements TaskQueuesExecutor{
	
	private static final RejectedTaskExecutionHandler defaultHandler =new AbortPolicy();
	private volatile RejectedTaskExecutionHandler handler;
	private volatile int corePoolSize;
    private volatile int maximumPoolSize;
    private volatile long keepAliveTime;
    private volatile boolean allowCoreThreadTimeOut;
    private volatile int largestPoolSize;
    
    public AbstractThreadPoolTaskQueuesExecutor(int corePoolSize,int maximumPoolSize,
            long keepAliveTime,TimeUnit unit,ThreadFactory threadFactory) {
			this(corePoolSize,maximumPoolSize,keepAliveTime,unit,threadFactory,defaultHandler);
	}
    
	public AbstractThreadPoolTaskQueuesExecutor(int corePoolSize,int maximumPoolSize,
            long keepAliveTime,TimeUnit unit,ThreadFactory threadFactory,RejectedTaskExecutionHandler handler) {
			super(threadFactory);
			if (corePoolSize < 0 ||maximumPoolSize <= 0 ||
			maximumPoolSize < corePoolSize ||keepAliveTime < 0)
			{
				throw new IllegalArgumentException();
			}
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.keepAliveTime = unit.toNanos(keepAliveTime);
			this.handler = handler;
	}
	
    public boolean allowsCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }
    
    public void allowCoreThreadTimeOut(boolean value) {
        if (value && keepAliveTime <= 0)
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        if (value != allowCoreThreadTimeOut) {
            allowCoreThreadTimeOut = value;
        }
    }
	
	public void setKeepAliveTime(long time, TimeUnit unit) {
        if (time < 0)
            throw new IllegalArgumentException();
        if (time == 0 && allowsCoreThreadTimeOut())
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        long keepAliveTime = unit.toNanos(time);
        this.keepAliveTime = keepAliveTime;
    }
	
    public long getKeepAliveTime(TimeUnit unit) {
        return unit.convert(keepAliveTime, TimeUnit.NANOSECONDS);
    }

	public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize < 0)
            throw new IllegalArgumentException();
        this.corePoolSize = corePoolSize;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }
    
    public void setMaximumPoolSize(int maximumPoolSize) {
        if (maximumPoolSize <= 0 || maximumPoolSize < corePoolSize)
            throw new IllegalArgumentException();
        this.maximumPoolSize = maximumPoolSize;
    }
    
    public final int getMaximumPoolSize() {
        return maximumPoolSize;
    }
    
    public int getLargestPoolSize() {
		return largestPoolSize;
	}

	public void setLargestPoolSize(int largestPoolSize) {
		this.largestPoolSize = largestPoolSize;
	}

	/**
     * 获取活动现场数量
     * @return
     */
    public abstract int getActiveCount();
    
    /**
     * 获取池大小
     * @return
     */
    public abstract int getPoolSize();
    
    /**
     * 是否关闭
     * @return
     */
    public abstract boolean isTerminating();
    
    /**
     * 重启所有核心线程
     * @return
     */
    public abstract int prestartAllCoreThreads();
    
    /**
     * 重启核心线程
     * @return
     */
    public abstract boolean prestartCoreThread();
    
    /**
     * 获取完成任务数量
     * @return
     */
    public abstract long getCompletedTaskCount();
    
    protected void beforeExecute(Thread t, Runnable r) { }

    protected void afterExecute(Runnable r, Throwable t) { }

    protected void terminated() { }
    
    public final void setRejectedExecutionHandler(RejectedTaskExecutionHandler handler) {
	    if (handler == null)
	        throw new NullPointerException();
	    this.handler = handler;
	}

	public final RejectedTaskExecutionHandler getRejectedExecutionHandler() {
	    return handler;
	}
	
	public static interface RejectedTaskExecutionHandler {

        void rejectedExecution(Runnable r,AbstractTaskQueuesExecutor executor);
    }

    /**
     * A handler for rejected tasks that throws a
     * {@code RejectedExecutionException}.
     */
    public static class AbortPolicy implements RejectedTaskExecutionHandler {
        /**
         * Creates an {@code AbortPolicy}.
         */
        public AbortPolicy() { }

        /**
         * Always throws RejectedExecutionException.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         * @throws RejectedExecutionException always
         */
        public void rejectedExecution(Runnable r, AbstractTaskQueuesExecutor e) {
            throw new RejectedExecutionException("Task " + r.toString() +
                                                 " rejected from " +
                                                 e.toString());
        }
    }
}
