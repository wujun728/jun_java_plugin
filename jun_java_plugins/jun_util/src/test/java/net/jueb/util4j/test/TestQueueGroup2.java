package net.jueb.util4j.test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import org.jctools.queues.MpscLinkedQueue;
import org.jctools.queues.atomic.MpmcAtomicArrayQueue;

import com.jun.plugin.util4j.queue.queueExecutor.QueueFactory;
import com.jun.plugin.util4j.queue.queueExecutor.RunnableQueue;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.IndexQueueGroupManager;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.KeyQueueGroupManager;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.QueueGroupExecutor;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.impl.DefaultIndexQueueManager;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.impl.DefaultKeyQueueManager;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.impl.DefaultQueueGroupExecutor;
import com.jun.plugin.util4j.queue.queueExecutor.queue.RunnableQueueWrapper;

public class TestQueueGroup2 {

	public static void main(String[] args) {
	}
	
	protected QueueGroupExecutor buildByJdk(int min,int max)
	{
		//多生产多消费者队列(线程竞争队列)
		Queue<Runnable> bossQueue=new ConcurrentLinkedQueue<>();
		QueueFactory qf=new QueueFactory() {
			@Override
			public RunnableQueue buildQueue() {
				Queue<Runnable> queue=new ConcurrentLinkedQueue<>();
				return new RunnableQueueWrapper(queue);
			}
		};
		IndexQueueGroupManager iqm=new DefaultIndexQueueManager(qf);
		KeyQueueGroupManager kqm=new DefaultKeyQueueManager(qf);
		DefaultQueueGroupExecutor.Builder b=new DefaultQueueGroupExecutor.Builder();
		b.setAssistExecutor(Executors.newSingleThreadExecutor());
		return b.setMaxPoolSize(max).setCorePoolSize(min).setBossQueue(bossQueue).setIndexQueueGroupManager(iqm).setKeyQueueGroupManagerr(kqm).build();
	}
	
	protected QueueGroupExecutor buildByMpMc(int min,int max,int maxPendingTask)
	{
		int maxQueueCount=maxPendingTask;
		//多生产多消费者队列(线程竞争队列)
		Queue<Runnable> bossQueue=new MpmcAtomicArrayQueue<>(maxQueueCount);
		QueueFactory qf=new QueueFactory() {
			@Override
			public RunnableQueue buildQueue() {
				//多生产单消费者队列(PS:bossQueue决定了一个队列只能同时被一个线程处理)
				Queue<Runnable> queue=new MpscLinkedQueue();
				return new RunnableQueueWrapper(queue);
			}
		};
		IndexQueueGroupManager iqm=new DefaultIndexQueueManager(qf,false);
		KeyQueueGroupManager kqm=new DefaultKeyQueueManager(qf);
		DefaultQueueGroupExecutor.Builder b=new DefaultQueueGroupExecutor.Builder();
		b.setAssistExecutor(Executors.newSingleThreadExecutor());
		return b.setMaxPoolSize(max).setCorePoolSize(min).setBossQueue(bossQueue).setIndexQueueGroupManager(iqm).setKeyQueueGroupManagerr(kqm).build();
	}
}
