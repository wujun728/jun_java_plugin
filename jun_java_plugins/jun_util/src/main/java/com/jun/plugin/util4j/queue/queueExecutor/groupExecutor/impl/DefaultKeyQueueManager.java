package com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.jun.plugin.util4j.queue.queueExecutor.QueueFactory;
import com.jun.plugin.util4j.queue.queueExecutor.executor.QueueExecutor;
import com.jun.plugin.util4j.queue.queueExecutor.executor.impl.RunnableQueueExecutorEventWrapper;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.KeyQueueGroupManager;

public class DefaultKeyQueueManager extends AbstractQueueMaganer implements KeyQueueGroupManager{

	private final Map<String,TaskQueue> queues=new HashMap<>();
	private final Map<String,String> alias=new HashMap<>();
	private final AtomicLong totalCompleteTask=new AtomicLong();
	
	private final Object addLock=new Object();

	private volatile KeyGroupEventListener listener;

	public DefaultKeyQueueManager() {
		
	}
	
	public DefaultKeyQueueManager(QueueFactory queueFactory) {
		super(queueFactory);
	}

	public void execute(String index, Runnable task) {
		if (index==null || task == null) {
			throw new IllegalArgumentException();
		}
		getQueueExecutor(index).execute(task);
	}

	public void execute(String index, List<Runnable> tasks) {
		if (index==null || tasks == null) {
			throw new IllegalArgumentException();
		}
		getQueueExecutor(index).execute(tasks);
	}
	
	public void setGroupEventListener(KeyGroupEventListener listener)
	{
		this.listener=listener;
	}
	
	public void setAlias(String index, String alias) {
		this.alias.put(index, alias);
	}

	public String getAlias(String index) {
		return this.alias.get(index);
	}

	public QueueExecutor getQueueExecutor(String index) {
		if (index==null) {
			throw new IllegalArgumentException();
		}
		QueueExecutor qe=queues.get(index);
		if(qe==null)
		{
			synchronized (addLock) {
				if(qe==null)
				{
					TaskQueue tq=new TaskQueue(index,getQueueFactory_().buildQueue());
					queues.put(index,tq);
					return tq;
				}
			}
		}
		return qe;
	}

	/**
	 * 获取总完成任务数量
	 * @return
	 */
	public long getToalCompletedTaskCount() {
	    return totalCompleteTask.get();
	}

	@Override
	public long getToalCompletedTaskCount(String index) {
		TaskQueue tq=queues.get(index);
		if(tq!=null)
		{
			return tq.getCompletedTaskCount().get();
		}
		return 0;
	}

	public Iterator<QueueExecutor> iterator() {
		return new Iterator<QueueExecutor>() {
			final Iterator<TaskQueue> map=queues.values().iterator();
			@Override
			public boolean hasNext() {
				return map.hasNext();
			}
	
			@Override
			public QueueExecutor next() {
				return map.next();
			}
		};
	}

	/**
	 * key对应的队里产生了处理任务
	 * @param key
	 * @param handleTask
	 */
	protected void onQueueHandleTask(String key,Runnable handleTask)
	{
		KeyGroupEventListener tmp=listener;
		if(tmp!=null)
		{//任务抛给监听者
			tmp.onQueueHandleTask(key, handleTask);
		}
	}

	/**
	 * 插槽队列
	 * @author juebanlin
	 */
	private class TaskQueue extends RunnableQueueExecutorEventWrapper implements Runnable{
		/**
		 *队列索引
		 */
		private final String index;
		/**
	     * 此队列是否锁定/是否被线程占用
	     * 次属性仅供本类持有
	     */
	    private final AtomicBoolean isLock = new AtomicBoolean(false);
	    
	    private final AtomicBoolean processLock = new AtomicBoolean(false);
	    
	    /**
	     * 此队列完成的任务数量
	     */
		private final AtomicLong completedTaskCount = new AtomicLong(0);
		
		public TaskQueue(String index,Queue<Runnable> queue) {
			super(queue,"TaskQueue-"+index);
			this.index=index;
			init();
		}
		
		@Override
		public String getQueueName() {
			return getAlias(index);
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
	
		@Override
		protected void onAddBefore() {
			
		}

		@Override
		protected void onAddAfter(boolean offeredSucceed) {
			if(offeredSucceed)
			{
				if(isLock.compareAndSet(false, true))
			 	{//一个处理任务产生
//					onQueueHandleTask(index,new QueueProcessTask(this));
					onQueueHandleTask(index,this);
			 	}
			}
		}

		protected void beforeExecute(Thread thread, Runnable task) {
			
		}
	
		protected void afterExecute(Runnable task,RuntimeException object) {
			
		}

		@Override
		public void run() {
			TaskQueue queue=this;
			if(queue.processLock.compareAndSet(false, true))
			{//如果此runnable未被执行则执行,已执行则不可再次执行
				try {
					handleQueueTask(queue);
				} finally {
					queue.processLock.set(false);
					queue.isLock.set(false);
				}
			}
		}
		
		/**
         * 处理队列任务
         * @param queue
         */
        private void handleQueueTask(TaskQueue queue) {
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
                    totalCompleteTask.incrementAndGet();
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

		@SuppressWarnings("unused")
		@Deprecated
		private class QueueProcessTask implements Runnable{
			TaskQueue queue;
			private QueueProcessTask(TaskQueue queue) {
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
		    private void handleQueueTask(TaskQueue queue) {
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
		                totalCompleteTask.incrementAndGet();
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
	
	public static class Builder{
		QueueFactory queueFactory=DefaultQueueFactory;
		
		public Builder setQueueFactory(QueueFactory queueFactory) {
			Objects.requireNonNull(queueFactory);
			this.queueFactory = queueFactory;
			return this;
		}
		/**
		 * 设置多生产者单消费者队列工厂
		 * @return 
		 */
		public Builder setMpScQueueFactory() {
			this.queueFactory=QueueFactory.MPSC_QUEUE_FACTORY;
			return this;
		}

		public DefaultKeyQueueManager build()
		{
			return new DefaultKeyQueueManager(queueFactory);
		}
	}
}