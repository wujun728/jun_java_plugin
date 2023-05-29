package com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.jun.plugin.util4j.queue.queueExecutor.QueueFactory;
import com.jun.plugin.util4j.queue.queueExecutor.executor.QueueExecutor;
import com.jun.plugin.util4j.queue.queueExecutor.executor.impl.RunnableQueueExecutorEventWrapper;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.IndexQueueGroupManager;

public class DefaultIndexQueueManager extends AbstractQueueMaganer implements IndexQueueGroupManager{
	/**
	 * 最大队列插槽数
	 */
	public static final int MAX_SOLT_COUNT = 0xFFFF;

	/**
	 * 队列插槽
	 */
	private final SoltQueue[] solts = new SoltQueue[MAX_SOLT_COUNT + 1];

	/**
	 * 队列别名
	 */
	private final String[] soltAlias = new String[solts.length];
	
	private final AtomicLong totalCompleteTask=new AtomicLong();
	
	private volatile IndexGroupEventListener listener;

	public DefaultIndexQueueManager() {
		
	}
	
	public DefaultIndexQueueManager(QueueFactory queueFactory) {
		this(queueFactory, false);
	}
	
	public DefaultIndexQueueManager(QueueFactory queueFactory,boolean initQueues) {
		super(queueFactory);
		if(initQueues)
		{
			initQueues();
		}
	}
	
	protected void initQueues()
    {
		for(int i=0;i<solts.length;i++)
    	{
    		solts[i]=new SoltQueue(i,getQueueFactory_().buildQueue());
    	}
    }
	
	/**
	 * 转换为插槽索引
	 * 
	 * @param solt
	 * @return
	 */
	@Deprecated
	protected final int convertIndex_old(short solt) {
		byte a = (byte) (solt >> 8 & 0xFF);// 高8位
		byte b = (byte) (solt & 0xFF);// 低8位
		int value = ((int) (a)) << 8 | (int) (b);
		return value & 0xffff;
	}
	/**
	 * 转换为插槽索引
	 * @param solt
	 * @return
	 */
	protected final int convertIndex(short solt) {
		return solt & 0xFFFF;
	}

	public Iterator<QueueExecutor> iterator() {
		return new Iterator<QueueExecutor>() {
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < solts.length;
			}

			@Override
			public QueueExecutor next() {
				return solts[i++];
			}
		};
	}

	public void execute(short solt, Runnable task) {
		if (task == null) {
			throw new RuntimeException("task is null");
		}
		getQueueExecutor(solt).execute(task);
	}

	public void execute(short solt, List<Runnable> tasks) {
		if (tasks == null) {
			throw new RuntimeException("tasks is null");
		}
		getQueueExecutor(solt).execute(tasks);
	}

	public void setAlias(short solt, String alias) {
		soltAlias[convertIndex(solt)] = alias;
	}

	public String getAlias(short solt) {
		return soltAlias[convertIndex(solt)];
	}

	public QueueExecutor getQueueExecutor(short solt) {
		int index = convertIndex(solt);
		if(solts[index]==null)
		{
			synchronized (solts) {
				if(solts[index]==null)
				{
					solts[index]=new SoltQueue(index,getQueueFactory_().buildQueue());
				}
			}
		}
		return solts[index];
	}
	
	public void setGroupEventListener(IndexGroupEventListener listener)
	{
		this.listener=listener;
	}
	
	protected void onQueueHandleTask(short solt,Runnable handleTask)
	{
		IndexGroupEventListener tmp=listener;
		if(tmp!=null)
		{
			tmp.onQueueHandleTask(solt, handleTask);
		}
	}
	
	public long getToalCompletedTaskCount(short solt) {
		int index = convertIndex(solt);
		return solts[index].getCompletedTaskCount().get();
	}

	/**
	 * 获取总完成任务数量
	 * @return
	 */
	public long getToalCompletedTaskCount() {
        return totalCompleteTask.get();
    }
	
	/**
	 * 插槽队列,具有增删事件包装于queueFactory生产的queue
	 * @author juebanlin
	 */
	private class SoltQueue extends RunnableQueueExecutorEventWrapper implements Runnable{
		/**
		 * 队列索引
		 */
		private final int soltIndex;
		
		/**
	     * 处理任务生成锁
	     */
	    private final AtomicBoolean isLock = new AtomicBoolean(false);
	    
	    /**
	     * 任务处理锁
	     */
	    private final AtomicBoolean processLock = new AtomicBoolean(false);
	    /**
	     * 此队列完成的任务数量
	     */
		private final AtomicLong completedTaskCount = new AtomicLong(0);
		
		public SoltQueue(int soltIndex,Queue<Runnable> queue) {
			super(queue,"SoltQueue-"+soltIndex);
			this.soltIndex=soltIndex;
			init();
		}
		
		@Override
		public String getQueueName() {
			return getAlias((short) soltIndex);
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
//					onQueueHandleTask((short)soltIndex,new SoltQueueProcessTask(this));
					onQueueHandleTask((short)soltIndex,this);
			 	}
			}
		}

		protected void beforeExecute(Thread thread, Runnable task) {
			
		}

		protected void afterExecute(Runnable task,RuntimeException object) {
			
		}

		@Override
		public void run() {
			SoltQueue queue=this;
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
		QueueFactory queueFactory=QueueFactory.DEFAULT_QUEUE_FACTORY;
		boolean initQueues;
		public Builder setQueueFactory(QueueFactory queueFactory) {
			Objects.requireNonNull(queueFactory);
			this.queueFactory = queueFactory;
			return this;
		}
		
		/**
		 * 是否提前初始化队列
		 * @param initQueues
		 * @return
		 */
		public Builder setInitQueues(boolean initQueues)
		{
			this.initQueues=initQueues;
			return this;
		}
		
		/**
		 * 设置多生产者单消费者队列工厂
		 */
		public Builder setMpScQueueFactory() {
			this.queueFactory=QueueFactory.MPSC_QUEUE_FACTORY;
			return this;
		}

		public DefaultIndexQueueManager build()
		{
			return new DefaultIndexQueueManager(queueFactory,initQueues);
		}
	}
}