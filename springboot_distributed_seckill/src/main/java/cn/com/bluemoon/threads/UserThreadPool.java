package cn.com.bluemoon.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 多线程编程
 * @author Wujun
 * @date 2019年5月24日 下午5:19:35
 *
 */
public class UserThreadPool {
	
	public static void main(String[] args) {
		//缓存队列设置固定长度为2，为了快速出发rejectHandler
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(2);
		//假设外部任务线程的来源由机房1和机房2的混合调用
		UserThreadFactory f1 = new UserThreadFactory("第1机房");
		UserThreadFactory f2 = new UserThreadFactory("第2机房");
		
		UserRejectHandler handler = new UserRejectHandler();
		
		//核心线程为1，最大线程为2，为了保证触发rejectHandler
		ThreadPoolExecutor threadPoolFirst = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, queue, f1, handler);
		//利用第二个线程工厂实例创建第二个线程池
		ThreadPoolExecutor threadPoolSecond = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, queue, f2, handler);
		
		//创建400个任务线程
		Runnable task = new Task();
		for(int i = 0; i < 200; i++ ) {
			threadPoolFirst.execute(task);
			threadPoolSecond.execute(task);
		}
	}
	
	static class Task implements Runnable{
		private final AtomicLong count = new AtomicLong(0L);
		
		@Override
		public void run() {
			System.out.println("running_" + count.getAndIncrement());
		}
	}
	
}
