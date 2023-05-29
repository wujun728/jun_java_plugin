package com.jun.plugin.util4j.queue.queueExecutor.groupExecutor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 任务队列组执行器服务
 * QueueGroupExecutor中空闲的线程将用来作为ExecutorService
 * 当所有的线程都在执行ExecutorService提交的任务时,如果有队列任务添加,要么触发新线程启动,要么等待
 * 注意:此服务提交的任务可能会因为最大线程数量限制和任务阻塞影响队列任务的执行
 * @author juebanlin
 */
public interface QueueGroupExecutorService extends QueueGroupExecutor{
	
	void execute(Runnable task);
	
	<T> Future<T> submit(Callable<T> task);

	<T> Future<T> submit(Runnable task, T result);

	Future<?> submit(Runnable task);

	<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException;

	<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException;

	<T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException;

	<T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException;
}
