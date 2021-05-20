package com.jun.plugin.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 计时任务
 */
public class ExecutorTimeTask {

	public static void main(String[] args) {
		
		System.out.println(Runtime.getRuntime().availableProcessors());

		long begin = System.currentTimeMillis();

		List<Integer> timeouts = Arrays.asList(3, 4, 1, 2, 5, 1, 2, 3, 1, 2, 5,
				4, 3);

		List<Future<Integer>> tasks = new ArrayList<Future<Integer>>(
				timeouts.size());

		int flag = 1;

		switch (flag) {
		case 1:

			ExecutorService exectuor1 = Executors.newFixedThreadPool(timeouts
					.size());

			CompletionService<Integer> service = new ExecutorCompletionService<Integer>(
					exectuor1);

			for (int i = 0; i < timeouts.size(); i++) {

				final int index = i;

				final Integer timeout = timeouts.get(i);

				tasks.add(service.submit(new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						int time = timeout * 1000;
						Thread.sleep(time);
						System.out.println("任务" + index + "睡眠" + time);
						return timeout;
					}

				}));

			}

			ExecutorService executor2 = Executors.newFixedThreadPool(timeouts
					.size());

			for (int i = 0; i < tasks.size(); i++) {

				final int index = i;

				final Future<Integer> f = tasks.get(index);

				executor2.execute(new Runnable() {

					@Override
					public void run() {

						try {
							f.get(2000, TimeUnit.MILLISECONDS);
						} catch (ExecutionException e) {
							System.err.println("执行线程异常");
							e.getCause().printStackTrace();
						} catch (TimeoutException e) {
							System.err.println("任务" + index + "超时、取消");
							f.cancel(true);
						} catch (InterruptedException e) {
							System.err.println("任务" + index + "中断");
						}
					}

				});

			}
			break;
		case 2:

			ExecutorService executor1 = Executors.newFixedThreadPool(timeouts
					.size());

			List<Callable<Integer>> callables = new ArrayList<Callable<Integer>>(
					timeouts.size());

			for (int i = 0; i < timeouts.size(); i++) {

				final int index = i;

				final Integer timeout = timeouts.get(i);

				callables.add(new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						int time = timeout * 1000;
						Thread.sleep(time);
						System.out.println("任务" + index + "睡眠" + time);
						return timeout;
					}
				});

			}

			try {
				executor1.invokeAll(callables, 2000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		long end = System.currentTimeMillis();

		System.out.println("用时" + (end - begin));

	}
}