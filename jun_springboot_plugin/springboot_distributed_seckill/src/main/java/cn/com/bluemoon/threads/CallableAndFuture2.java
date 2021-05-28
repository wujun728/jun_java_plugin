package cn.com.bluemoon.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用ExecutorService、Callable、Future实现有返回结果的多线程
 * @author Wujun
 * @date 2019年1月9日 下午4:37:32
 *
 */
public class CallableAndFuture2 {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("----程序开始运行----");
		long time1 = System.currentTimeMillis();
		
		int taskSize = 5;
		//创建一个线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(taskSize);
		//创建多个有返回值的任务
		List<Future> list = new ArrayList<Future>();
		for (int i = 0; i < taskSize; i++) {
			Callable callable = new MyCallable(i + "");
			//执行任务并获取Future对象
			Future future = threadPool.submit(callable);
			list.add(future);
		}
		
		//关闭线程池
		threadPool.shutdown();
		
		//获取所有并发任务的运行结果
		for( Future future : list ) {
			//从Future对象上获取任务的返回值，并输出到控制台
			System.out.println(">>>" + future.get().toString() );
		}
		long time2 = System.currentTimeMillis();
		System.out.println("----程序运行结束----，程序运行时间【" + (time2 - time1) + "毫秒】");
	}

	static class MyCallable implements Callable<Object> {

		private String taskNum;
		
		public MyCallable(String taskNum) {
			this.taskNum = taskNum;
		}
		
		@Override
		public Object call() throws Exception {
			System.out.println(">>>" + taskNum + "任务启动");
			long startTime = System.currentTimeMillis();
			if("1".equals(taskNum)) {
				Thread.sleep(1500);
			}else {
				Thread.sleep(1000);
			}
			long endTime = System.currentTimeMillis();
			System.out.println(">>>" + taskNum + "任务终止");
			return taskNum + "任务返回运行结果，当前任务运行时间【" + (endTime - startTime) + "毫秒】";
		}
		
	}
	
}
