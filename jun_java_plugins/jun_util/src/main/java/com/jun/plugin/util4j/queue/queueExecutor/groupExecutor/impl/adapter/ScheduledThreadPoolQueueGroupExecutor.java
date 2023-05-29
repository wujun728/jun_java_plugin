package com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.impl.adapter;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import com.jun.plugin.util4j.queue.queueExecutor.executor.QueueExecutor;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.IndexQueueGroupManager;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.KeyQueueGroupManager;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.QueueGroupExecutorService;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.IndexQueueGroupManager.IndexGroupEventListener;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.KeyQueueGroupManager.KeyGroupEventListener;

public class ScheduledThreadPoolQueueGroupExecutor extends ScheduledThreadPoolExecutor implements QueueGroupExecutorService{

    private final IndexQueueGroupManager iqm;
    private final KeyQueueGroupManager kqm;
    
    protected void init()
    {
    	this.iqm.setGroupEventListener(new IndexGroupEventListener() {
			@Override
			public void onQueueHandleTask(short index, Runnable handleTask) {
				//当sqm有可以处理某队列的任务产生时,丢到系统队列,当系统队列
				execute(handleTask);
			}
		});
    	this.kqm.setGroupEventListener(new KeyGroupEventListener() {
			@Override
			public void onQueueHandleTask(String key, Runnable handleTask) {
				//当sqm有可以处理某队列的任务产生时,丢到系统队列,当系统队列
				execute(handleTask);
			}
		});
    }
	
	public ScheduledThreadPoolQueueGroupExecutor(int corePoolSize, ThreadFactory threadFactory,RejectedExecutionHandler handler,
			IndexQueueGroupManager iqm,KeyQueueGroupManager kqm) {
		super(corePoolSize, threadFactory, handler);
		if (iqm==null || kqm==null)
		{
			throw new IllegalArgumentException();
		}
		this.iqm=iqm;
		this.kqm=kqm;
		init();
	}
	
	public Iterator<QueueExecutor> indexIterator() {
		return iqm.iterator();
	}

	@Override
	public void execute(short solt, Runnable task) {
		iqm.getQueueExecutor(solt).execute(task);
	}

	@Override
	public void execute(short solt, List<Runnable> tasks) {
		iqm.getQueueExecutor(solt).execute(tasks);
	}

	@Override
	public void setAlias(short solt, String alias) {
		iqm.setAlias(solt, alias);
	}

	@Override
	public String getAlias(short solt) {
		return iqm.getAlias(solt);
	}

	@Override
	public QueueExecutor getQueueExecutor(short solt) {
		return iqm.getQueueExecutor(solt);
	}

	@Override
	public void execute(String key, Runnable task) {
		kqm.getQueueExecutor(key).execute(task);
	}

	@Override
	public void execute(String key, List<Runnable> tasks) {
		kqm.getQueueExecutor(key).execute(tasks);
	}

	@Override
	public void setAlias(String key, String alias) {
		kqm.setAlias(key, alias);
	}

	@Override
	public String getAlias(String key) {
		return kqm.getAlias(key);
	}

	@Override
	public QueueExecutor getQueueExecutor(String key) {
		return kqm.getQueueExecutor(key);
	}

	@Override
	public Iterator<QueueExecutor> keyIterator() {
		return kqm.iterator();
	}
}

