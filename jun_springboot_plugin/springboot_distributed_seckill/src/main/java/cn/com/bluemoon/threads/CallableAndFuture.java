package cn.com.bluemoon.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 执行多个带返回值的任务，并取得多个返回值
 * 异步非阻塞获取并行任务执行结果
 * @author Wujun
 * @date 2019年1月9日 下午3:56:46
 *
 */
public class CallableAndFuture {
	
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
		for (int i = 0; i < 5; i++) {
			final int taskId = i;
			cs.submit(new Callable<Integer>() {
				
				@Override
				public Integer call() throws Exception {
					//taskId为3的时候等待3s，最后输出的结果永远是3，证明获取结果是非阻塞
					if( taskId == 3 ) {
						Thread.sleep(3000);
					}
					return taskId;
				}
			});
		}
		
		threadPool.shutdown();
		for (int i = 0; i < 5; i++) {
			try {
				System.out.println(cs.take().get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
