package net.jueb.util4j.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.jun.plugin.util4j.common.game.cdkey.CdkeyFactoryRandomImpl;

import net.jueb.util4j.queue.taskQueue.Task;
import net.jueb.util4j.queue.taskQueue.TaskQueueExecutor;
import net.jueb.util4j.queue.taskQueue.TaskQueuesExecutor;
import net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.multithread.ThreadPoolTaskQueuesExecutor;
import net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.multithread.mina.FixedThreadPoolBlockingQueuesExecutor;
import net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.multithread.mina.FixedThreadPoolQueuesExecutor;

public class TestQueues{
    	 public Task buildTask()
    	 {
    		 return new Task() {
				@Override
				public void run() {
					CdkeyFactoryRandomImpl cd=new CdkeyFactoryRandomImpl();
					cd.build();
					cd.build();
					cd.build();
					cd.build();
				}
				
				@Override
				public String name() {
					return "";
				}
			};
    	 }
    	 
    	 public void test(final int taskCount,final TaskQueueExecutor o)
		 {
    		 final AtomicLong addTime=new AtomicLong();
			 final Long[] startTime=new Long[1];
			 Runnable t=new Runnable() {
				public void run() {
					long time=System.currentTimeMillis();
					//开始时间标记任务
					o.execute(new Task() {
						@Override
						public void run() {
							startTime[0]=System.currentTimeMillis();
						}
						@Override
						public String name() {
							return "";
						}
					});
					time=System.currentTimeMillis()-time;
					addTime.addAndGet(time);
					//各个队列随机插入影响任务
					for(int i=1;i<=taskCount;i++)
					{
						Task t=buildTask();
						time=System.currentTimeMillis();
						o.execute(t);
						time=System.currentTimeMillis()-time;
						addTime.addAndGet(time);
					}
					//结束时间标记任务
					o.execute(new Task() {
						@Override
						public void run() {
							long time= System.currentTimeMillis()-startTime[0];
							System.err.println("最后一个任务完成,放入任务耗时:"+addTime.get()+",队列耗时:"+time+",当前线程ID:"+Thread.currentThread().getId());
						}
						@Override
						public String name() {
							return "";
						}
					});
				}
			};
			new Thread(t).start();
		 }

		public void test(final int taskCount,final int queueCount,final TaskQueuesExecutor o)
    	{
			for(int i=1;i<=queueCount;i++)
			{
				o.openQueue(i+"");
			}
    		 final Map<Integer,Long> startTime=new HashMap<>();
    		 final AtomicLong addTime=new AtomicLong();
    		 Runnable t=new Runnable() {
 				public void run() {
 					//开始时间标记任务
					for(int i=1;i<=queueCount;i++)
					{
						final int queueName=i;
						long t=System.currentTimeMillis();
						o.execute(queueName+"", new Task() {
							@Override
							public void run() {
								startTime.put(queueName, System.currentTimeMillis());
							}
							@Override
							public String name() {
								return "";
							}
						});
						t=System.currentTimeMillis()-t;
						addTime.addAndGet(t);
					}
					//各个队列随机插入影响任务
 					for(int i=1;i<=taskCount;i++)
 					{
 						Task t=buildTask();
 						int queue=RandomUtils.nextInt(queueCount)+1;//随机加入队列
 						long time=System.currentTimeMillis();
 						o.execute(queue+"",t);
 						time=System.currentTimeMillis()-time;
						addTime.addAndGet(time);
 					}
 					//结束时间标记任务
 					for(int i=1;i<=queueCount;i++)
 					{
 						final int queueName=i;
 						o.execute(queueName+"", new Task() {
							@Override
							public void run() {
								long time= System.currentTimeMillis()-startTime.get(queueName);
								System.err.println("队列："+queueName+",最后一个任务完成,添加队列耗时:"+addTime.get()+",队列总耗时:"+time+",当前线程ID:"+Thread.currentThread().getId());
							}
							@Override
							public String name() {
								return "";
							}
						});
 					}
 				}
 			};
 			new Thread(t).start();
    	 }
    	 
    	 public void testOrder(final TaskQueueExecutor o)
    	 {
    		 final AtomicInteger atomicInteger=new AtomicInteger(0);
    		 for(int i=0;i<1000;i++)
    		 {
    			 final int x=i;
				 o.execute(new Task() {
						@Override
						public void run() {
							int sleep=RandomUtils.nextInt(100);
							if(x%2==0)
							{
								System.err.println("i="+x+",value="+atomicInteger.incrementAndGet()+",sleep="+sleep);
							}else
							{
								System.err.println("i="+x+",value="+atomicInteger.decrementAndGet()+",sleep="+sleep);
							}
//							try {
//								Thread.sleep(sleep);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
						}
						@Override
						public String name() {
							return "";
						}
					});
    		 }
    	 }
    	 
    	 public void testOrder(final int taskCount,final int queueCount,final TaskQueuesExecutor o)
    	 {
    		 final Map<String,AtomicInteger> map1=new HashMap<>();
    		 final Map<String,AtomicInteger> map2=new HashMap<>();
    		 for(int i=1;i<=queueCount;i++)
    		 {
    			 map1.put(""+i, new AtomicInteger());
    			 map2.put(""+i, new AtomicInteger());
    			 o.openQueue(i+"");
    		 }
    		 final Map<Integer,Long> startTime=new HashMap<>();
    		 final AtomicLong addTime=new AtomicLong();
    		 Runnable t=new Runnable() {
 				public void run() {
 					//开始时间标记任务
					for(int i=1;i<=queueCount;i++)
					{
						final int queueName=i;
						long t=System.currentTimeMillis();
						o.execute(queueName+"", new Task() {
							@Override
							public void run() {
								startTime.put(queueName, System.currentTimeMillis());
							}
							@Override
							public String name() {
								return "";
							}
						});
						t=System.currentTimeMillis()-t;
						addTime.addAndGet(t);
					}
					//各个队列随机插入影响任务
 					for(int i=1;i<=taskCount;i++)
 					{
 						final int queue=RandomUtils.nextInt(queueCount)+1;//随机加入队列
 						final String queueName=queue+"";
 						Task t=new Task() {
							@Override
							public void run() {
								int sleep=RandomUtils.nextInt(100);
								AtomicInteger count=map1.get(queueName);
								AtomicInteger value=map2.get(queueName);
								int index=count.incrementAndGet();
								if(index%2==0)
								{
									value.incrementAndGet();
//									System.err.println("i="+index+",value="+value.incrementAndGet()+",sleep="+sleep);
								}else
								{
									value.decrementAndGet();
//									System.err.println("i="+index+",value="+value.decrementAndGet()+",sleep="+sleep);
								}
//								try {
//									Thread.sleep(sleep);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
							}
							@Override
							public String name() {
								return null;
							}
						};
 						
 						long time=System.currentTimeMillis();
 						o.execute(queueName+"",t);
 						time=System.currentTimeMillis()-time;
						addTime.addAndGet(time);
 					}
 					//结束时间标记任务
 					for(int i=1;i<=queueCount;i++)
 					{
 						final int queue=i;
 						final String queueName=i+"";
 						o.execute(queueName+"", new Task() {
							@Override
							public void run() {
								long time= System.currentTimeMillis()-startTime.get(queue);
								AtomicInteger count=map1.get(queueName);
								AtomicInteger value=map2.get(queueName);
								System.err.println("队列："+queueName+"count="+count+",value="+value+"最后一个任务完成,添加队列耗时:"+addTime.get()+",队列总耗时:"+time+",当前线程ID:"+Thread.currentThread().getId());
							}
							@Override
							public String name() {
								return "";
							}
						});
 					}
 				}
 			};
 			new Thread(t).start();
    	 }
    	 
    	 public static void main(String[] args) throws InterruptedException {
    		 TestQueues tq=new TestQueues();
    		 int qt=1000000;//每个队列测试任务数量 
    		 long t=System.currentTimeMillis();
			 Thread.sleep(5000);
			 int queueCount=20;
			 System.out.println("队列测试开始");
//			 FixedThreadPoolQueuesExecutor_mina_disruptor queue3=new FixedThreadPoolQueuesExecutor_mina_disruptor(2,4);
//    		 t=System.currentTimeMillis();
//    		 for(int i=1;i<=qt;i++)
//			 {
//    			queue3.execute(RandomUtils.nextInt(queueCount)+"", tq.buildTask());
//			 }
//			 System.err.println("queue3存入耗时："+(System.currentTimeMillis()-t));//3932
//			 
//			 FixedThreadPoolQueuesExecutor_mina queue4=new FixedThreadPoolQueuesExecutor_mina(2,8);
//			 t=System.currentTimeMillis();
//			 for(int i=1;i<=qt*queueCount;i++)
//			 {
//    			queue4.execute(RandomUtils.nextInt(queueCount)+"", tq.buildTask());
//			 }
//			 System.err.println("queue4存入耗时："+(System.currentTimeMillis()-t));
			 
//			 FixedThreadPoolQueuesExecutor queue5=new FixedThreadPoolQueuesExecutor(1,8,new LiteBlockingWaitConditionStrategy());
//			 t=System.currentTimeMillis();
//			 for(int i=1;i<=qt*queueCount;i++)
//			 {
//    			queue5.execute(RandomUtils.nextInt(queueCount)+"", tq.buildTask());
//			 }
//			 System.err.println("queue5存入耗时："+(System.currentTimeMillis()-t));
//			 queue.clear();
//			 queue2.clear();
//			 Thread.sleep(10000);
    		/**
    		 * 多队列多线程测试
    		 */
    		TaskQueuesExecutor ft=new FixedThreadPoolQueuesExecutor(1,8);
			tq.test(qt*20,20, ft);
//			队列：1,最后一个任务完成,添加队列耗时:2710,队列总耗时:2976,当前线程ID:15
//			 队列：3,最后一个任务完成,添加队列耗时:2710,队列总耗时:2976,当前线程ID:16
//			 队列：5,最后一个任务完成,添加队列耗时:2710,队列总耗时:2976,当前线程ID:15
//			 队列：4,最后一个任务完成,添加队列耗时:2710,队列总耗时:2976,当前线程ID:17
//			 队列：2,最后一个任务完成,添加队列耗时:2710,队列总耗时:2976,当前线程ID:14
//			 队列：8,最后一个任务完成,添加队列耗时:2710,队列总耗时:2976,当前线程ID:17
//			 队列：7,最后一个任务完成,添加队列耗时:2710,队列总耗时:2976,当前线程ID:15
//			 队列：6,最后一个任务完成,添加队列耗时:2710,队列总耗时:2976,当前线程ID:16
//			 
//    		TaskQueuesExecutor ft=new FixedThreadPoolQueuesExecutor_mina_disruptor(4,8);
//     		tq.test(qt*8,8, ft);
//     		队列：4,最后一个任务完成,添加队列耗时:2031,队列总耗时:126,当前线程ID:22
//     		队列：6,最后一个任务完成,添加队列耗时:2031,队列总耗时:136,当前线程ID:20
//     		队列：7,最后一个任务完成,添加队列耗时:2031,队列总耗时:126,当前线程ID:21
//     		队列：3,最后一个任务完成,添加队列耗时:2031,队列总耗时:136,当前线程ID:22
//     		队列：5,最后一个任务完成,添加队列耗时:2031,队列总耗时:141,当前线程ID:19
//     		队列：8,最后一个任务完成,添加队列耗时:2031,队列总耗时:131,当前线程ID:20
//     		队列：1,最后一个任务完成,添加队列耗时:2031,队列总耗时:126,当前线程ID:21
//     		队列：2,最后一个任务完成,添加队列耗时:2031,队列总耗时:131,当前线程ID:26
     		
//			ThreadPoolTaskQueuesExecutor ft=new ThreadPoolTaskQueuesExecutor(2,8);
//    		tq.test(qt*10,10, ft);
//    		队列：5,最后一个任务完成,添加队列耗时:4137,队列总耗时:40659,当前线程ID:14
//    		队列：7,最后一个任务完成,添加队列耗时:4137,队列总耗时:40919,当前线程ID:15
//    		队列：1,最后一个任务完成,添加队列耗时:4137,队列总耗时:41280,当前线程ID:20
//    		队列：8,最后一个任务完成,添加队列耗时:4137,队列总耗时:41376,当前线程ID:21
//    		队列：9,最后一个任务完成,添加队列耗时:4137,队列总耗时:41547,当前线程ID:17
//    		队列：6,最后一个任务完成,添加队列耗时:4137,队列总耗时:41559,当前线程ID:18
//    		队列：3,最后一个任务完成,添加队列耗时:4137,队列总耗时:41933,当前线程ID:16
//    		队列：10,最后一个任务完成,添加队列耗时:4137,队列总耗时:42023,当前线程ID:19
//    		队列：4,最后一个任务完成,添加队列耗时:4137,队列总耗时:47730,当前线程ID:15
//    		队列：2,最后一个任务完成,添加队列耗时:4137,队列总耗时:47871,当前线程ID:14
    		
//    		tq.test(qt*5,10, ft);
//    		队列：8,最后一个任务完成,添加队列耗时:1199,队列总耗时:20162,当前线程ID:19
//    		队列：3,最后一个任务完成,添加队列耗时:1199,队列总耗时:20162,当前线程ID:20
//    		队列：2,最后一个任务完成,添加队列耗时:1199,队列总耗时:20231,当前线程ID:15
//    		队列：9,最后一个任务完成,添加队列耗时:1199,队列总耗时:20286,当前线程ID:18
//    		队列：5,最后一个任务完成,添加队列耗时:1199,队列总耗时:20337,当前线程ID:21
//    		队列：1,最后一个任务完成,添加队列耗时:1199,队列总耗时:20407,当前线程ID:17
//    		队列：6,最后一个任务完成,添加队列耗时:1199,队列总耗时:20439,当前线程ID:14
//    		队列：4,最后一个任务完成,添加队列耗时:1199,队列总耗时:20457,当前线程ID:16
//    		队列：7,最后一个任务完成,添加队列耗时:1199,队列总耗时:23652,当前线程ID:19
//    		队列：10,最后一个任务完成,添加队列耗时:1199,队列总耗时:23653,当前线程ID:20
    		Thread.sleep(100000);
		}
}