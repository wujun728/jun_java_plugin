package net.jueb.util4j.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import org.jctools.queues.MpmcArrayQueue;
import org.jctools.queues.atomic.MpmcAtomicArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.QueueGroupExecutor;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.impl.DefaultQueueGroupExecutor;

public class TestQueueGroup {
	
	private Logger log=LoggerFactory.getLogger(getClass());
	
	/**
	 * 单消费者测试
	 * @param queueCount
	 * @param tasks
	 * @param o
	 * @throws InterruptedException
	 */
	public void SignleProducerTest(final short queueCount, final List<Runnable> tasks, final QueueGroupExecutor o) throws InterruptedException {
		final CountDownLatch latch=new CountDownLatch(queueCount);
		final Long[] startTime=new Long[queueCount];
		final AtomicLong addTime = new AtomicLong();
		final AtomicLong totalTime =  new AtomicLong();
		final AtomicLong runTotalTime = new AtomicLong();//总共耗时
//		final Random ran = new Random();
		Runnable t = new Runnable() {
			public void run() {
				totalTime.set(System.currentTimeMillis());
				log.debug("测试线程开始");
				// 开始时间标记任务
				for (int i = 0; i <queueCount; i++) {
					final short queueName = (short) i;
					long t = System.currentTimeMillis();
					o.execute(queueName, new Runnable() {
						@Override
						public void run() {
							startTime[queueName]=System.currentTimeMillis();
						}
					});
					t = System.currentTimeMillis() - t;
					addTime.addAndGet(t);
				}
				// 各个队列随机插入影响任务
				short num=0;
				for (Runnable rask : tasks) {
					num++;
					short queue=(short) (num%queueCount);//平均分配
//					short queue = (short) (ran.nextInt(queueCount));// 随机加入队列
					long time = System.currentTimeMillis();
					o.execute(queue, rask);
					time = System.currentTimeMillis() - time;
					addTime.addAndGet(time);
				}
				// 结束时间标记任务
				for (int i = 0; i < queueCount; i++) {
					final short queueName = (short) i;
					o.execute(queueName, new Runnable() {
						@Override
						public void run() {
							Long t = startTime[queueName];
							if (t == null) {
								log.error("null:" + queueName);
								return;
							}
							long time = System.currentTimeMillis() - t;
							runTotalTime.addAndGet(time);
							latch.countDown();
//							log.debug("队列：" + queueName + ",最后一个任务完成,添加队列耗时:" + addTime.get() + ",队列总耗时:"
//									+ time + ",当前线程ID:" + Thread.currentThread().getId() + ",count=" + count.get());
						}
					});
				}
				log.debug("测试线程结束");
			}
		};
		new Thread(t).start();
		latch.await();
		long total = System.currentTimeMillis() - totalTime.get();
		log.debug("生产耗时:"+addTime);
		log.debug("单消费耗时(串行):"+runTotalTime.get()+",(并行):"+total);
	}
	
	/**
	 * 多生产者测试
	 * @param producerCount
	 * @param queueCount
	 * @param tasks
	 * @param o
	 * @throws InterruptedException
	 */
	public void MultiProducerTest(int producerCount,final short queueCount, final List<Runnable> tasks, final QueueGroupExecutor o) throws InterruptedException {
		final CountDownLatch latch=new CountDownLatch(queueCount);
		final Long[] startTime=new Long[queueCount];
		final AtomicLong totalTime =  new AtomicLong();
		final AtomicLong addTime =  new AtomicLong();
		final AtomicLong runTotalTime = new AtomicLong();//总共耗时
		final Queue<Runnable>[] all=new Queue[queueCount];
		//初始化线程需要执行的队列
		for(int i=0;i<queueCount;i++)
		{
			final short queueName = (short)i;
			all[queueName]=new ConcurrentLinkedQueue<>();
			all[queueName].add(new Runnable() {
				@Override
				public void run() {
					startTime[queueName]=System.currentTimeMillis();//标记队列开始时间
				}
			});
		}
		//平均分配任务
		int num=0;
		for (Runnable task : tasks) {
			num++;
			short queue=(short) (num%queueCount);//平均分配
			all[queue].add(task);
		}
		//分配结束任务
		for(int i=0;i<queueCount;i++)
		{
			final short queueName = (short)i;
			all[queueName].add(new Runnable() {
				@Override
				public void run() {
					Long t = startTime[queueName];
					if (t == null) {
						log.error("null:" + queueName);
						return;
					}
					long time = System.currentTimeMillis() - t;
					runTotalTime.addAndGet(time);
					latch.countDown();
//					log.debug("队列：" + queueName + ",最后一个任务完成,添加队列耗时:" + addTime.get() + ",队列总耗时:"
//							+ time + ",当前线程ID:" + Thread.currentThread().getId() + ",count=" + count.get());
				}
			});
		}
		totalTime.set(System.currentTimeMillis());
		for(int i=0;i<producerCount;i++)
		{
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(;;)
					{
						int emptyCount=0;
						for(int i=0;i<all.length;i++)
						{
							short queue=(short) i;
							Runnable r=all[i].poll();
							if(r==null)
							{
								emptyCount++;
							}else
							{
								o.execute(queue, r);
							}
						}
						if(emptyCount==all.length)
						{
							break;
						}
					}
				}
			}).start();
		}
		latch.await();
		long total = System.currentTimeMillis() - totalTime.get();
		log.debug("多消费者耗时(串行):"+runTotalTime.get()+",(并行):"+total);
	}
	
	
	public Runnable buildSortTask()
	{
		return new Runnable() {
			@Override
			public void run() {
				String s="hellowolrd";
				for(int i=0;i<10;i++)
				{
					s+=s;
				}
				byte[] data=s.getBytes();
				Arrays.sort(data);
			}
		};
	}

	public void testSignleProducerTest() throws InterruptedException {
		short queueCount=50;
		int inputCount = 10000 * 50;//任务数量
		int minThread = 1;
		int maxThread = 8;
		List<Runnable> tasks = new ArrayList<>();
		for (int i=0;i<inputCount;i++) {
			tasks.add(buildSortTask());
		}
		DefaultQueueGroupExecutor qe = new DefaultQueueGroupExecutor(minThread, maxThread);
		SignleProducerTest(queueCount, tasks, qe);
	}
	
	public void testMultiProducerTest() throws InterruptedException {
		short queueCount=50;
		int inputCount = 10000 * 100;//任务数量
		int minThread = 1;
		int maxThread = 8;
		int producerThread=4;
		List<Runnable> tasks = new ArrayList<>();
		for (int i=0;i<inputCount;i++) {
			tasks.add(buildSortTask());
		}
		QueueGroupExecutor qe = new TestQueueGroup2().buildByJdk(minThread,maxThread);
		MultiProducerTest(producerThread,queueCount, tasks, qe);
	}
	
	public void testMultiProducerTest2() throws InterruptedException {
		short queueCount=50;
		int inputCount = 10000 * 100;//任务数量
		int minThread = 4;
		int maxThread = 8;
		int producerThread=4;
		List<Runnable> tasks = new ArrayList<>();
		for (int i=0;i<inputCount;i++) {
			tasks.add(buildSortTask());
		}
		QueueGroupExecutor qe = new TestQueueGroup2().buildByMpMc(minThread,maxThread,queueCount);
		MultiProducerTest(producerThread,queueCount, tasks, qe);
	}
	
	public void t1()
	{
		long t=System.nanoTime();
		int count=10000*10;
		Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
		for (int i=0;i<count;i++) {
			tasks.add(buildSortTask());
		}
		System.out.println(System.nanoTime()-t);
	}
	
	public void t2()
	{
		long t=System.nanoTime();
		int count=10000*10;
		Queue<Runnable> tasks = new MpmcArrayQueue<>(count);
		for (int i=0;i<count;i++) {
			tasks.add(buildSortTask());
		}
		System.out.println(System.nanoTime()-t);
	}
	
	public void t3()
	{
		long t=System.nanoTime();
		int count=10000*10;
		Queue<Runnable> tasks = new MpmcAtomicArrayQueue<>(count);
		for (int i=0;i<count;i++) {
			tasks.add(buildSortTask());
		}
		System.out.println(System.nanoTime()-t);
	}

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		TestQueueGroup t = new TestQueueGroup();
		sc.nextLine();
//		t.t1();
//		sc.nextLine();
//		t.t2();
//		sc.nextLine();
//		t.t3();
//		sc.nextLine();
//		t.testSignleProducerTest();
//		sc.nextLine();
		t.testMultiProducerTest2();
		sc.nextLine();
	}
}
