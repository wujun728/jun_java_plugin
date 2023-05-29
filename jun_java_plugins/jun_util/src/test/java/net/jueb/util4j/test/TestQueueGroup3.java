package net.jueb.util4j.test;

import java.util.Queue;
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

public class TestQueueGroup3 {

	
	protected static QueueGroupExecutor buildByMpMc(int min,int max,int maxPendingTask)
	{
		int maxQueueCount=maxPendingTask;
		//多生产多消费者队列(线程竞争队列)
		Queue<Runnable> bossQueue=new MpmcAtomicArrayQueue<>(maxQueueCount);
		QueueFactory qf=new QueueFactory() {
			@Override
			public RunnableQueue buildQueue() {
				//多生产单消费者队列(PS:bossQueue决定了一个队列只能同时被一个线程处理)
				Queue<Runnable> queue= new MpscLinkedQueue();
				return new RunnableQueueWrapper(queue);
			}
		};
		IndexQueueGroupManager iqm=new DefaultIndexQueueManager(qf,false);
		KeyQueueGroupManager kqm=new DefaultKeyQueueManager(qf);
		DefaultQueueGroupExecutor.Builder b=new DefaultQueueGroupExecutor.Builder();
		b.setAssistExecutor(Executors.newSingleThreadExecutor());
		DefaultQueueGroupExecutor e= b.setMaxPoolSize(max).setCorePoolSize(min).setBossQueue(bossQueue).setIndexQueueGroupManager(iqm).setKeyQueueGroupManagerr(kqm).build();
		for(int i=0;i<max;i++)
		{
			e.wakeUpWorkerIfNecessary();
		}
		return e;
	}
	
	public static void main(String[] args) {
		QueueGroupExecutor qe=buildByMpMc(4, 4, 10000);
		
		qe.execute((short)0,new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	};
}
