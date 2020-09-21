/*   
 * Project: OSMP
 * FileName: ThreadPoolExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.tools.commons.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;

import com.osmp.tools.commons.ExecutorWrapper;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月11日 上午11:08:59
 */

public class ThreadPoolExecutorWrapper implements ExecutorWrapper, DisposableBean {
	private static final Log logger = LogFactory.getLog(ThreadPoolExecutorWrapper.class);
	private static final Lock lock = new ReentrantLock();
	private static final int DefaultCorePoolSize = 12;

	private static ExecutorService executorService;
	private int corePoolSize;

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	/**
	 * 
	 * @return
	 */
	public ExecutorService getExecutorService() {
		if (null == executorService || executorService.isShutdown()) {
			lock.lock();
			try {
				if (null == executorService || executorService.isShutdown()) {
					executorService = Executors.newScheduledThreadPool(corePoolSize <= 0 ? DefaultCorePoolSize
							: corePoolSize);
					logger.info("Succeed to created the executor service instance : " + executorService);
					printThreadPoolInfo();
				}
			} finally {
				lock.unlock();
			}
		}
		return executorService;
	}

	@Override
	public <T extends Runnable> void execute(T... commands) {
		printThreadPoolInfo();
		for (Runnable command : commands) {
			getExecutorService().execute(command);
		}
		printThreadPoolInfo();
	}

	@Override
	public void execute(Collection<? extends Runnable> commands) {
		execute(commands.toArray(new Runnable[] {}));
	}

	@Override
	public <T extends Runnable> void invoke(T... commands) throws InterruptedException, ExecutionException {
		Collection<Callable<Object>> callers = new ArrayList<Callable<Object>>();
		for (Runnable entry : commands) {
			callers.add(Executors.callable(entry));
		}
		List<Future<Object>> result = invokeAll(callers);
		checkIsError(result);
		logger.debug("invoke result :" + result);
	}

	@Override
	public void invoke(Collection<? extends Runnable> commands) throws InterruptedException, ExecutionException {
		Collection<Callable<Object>> callers = new ArrayList<Callable<Object>>();
		for (Runnable entry : commands) {
			callers.add(Executors.callable(entry));
		}
		List<Future<Object>> result = invokeAll(callers);
		checkIsError(result);
		logger.debug("invoke result :" + result);
	}

	private void checkIsError(List<Future<Object>> results) throws InterruptedException, ExecutionException {
		if (null != results && !results.isEmpty()) {
			for (Future<Object> future : results) {
				future.get();
			}
		}
	}

	@Override
	public <T> List<Future<T>> invokeAll(Callable<T>... commands) throws InterruptedException {
		Collection<Callable<T>> callers = new ArrayList<Callable<T>>();
		for (Callable<T> entry : commands) {
			callers.add(entry);
		}
		return invokeAll(callers);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<Callable<T>> commands) throws InterruptedException {
		printThreadPoolInfo();
		return this.getExecutorService().invokeAll(commands);
	}

	@Override
	public void printThreadPoolInfo() {
		if (logger.isDebugEnabled()) {
			if (executorService instanceof ThreadPoolExecutor) {
				ThreadPoolExecutor threadPool = (ThreadPoolExecutor) executorService;
				StringBuilder infoBuilder = new StringBuilder();
				infoBuilder.append("PoolSize : " + threadPool.getPoolSize());
				infoBuilder.append("\tCorePoolSize : " + threadPool.getCorePoolSize());
				infoBuilder.append("\tActiveCount : " + threadPool.getActiveCount());
				infoBuilder.append("\tLargestPoolSize : " + threadPool.getLargestPoolSize());
				infoBuilder.append("\tCompletedTaskCount : " + threadPool.getCompletedTaskCount());
				infoBuilder.append("\tTaskCount : " + threadPool.getTaskCount());
				infoBuilder.append("\tIsShutdown : " + threadPool.isShutdown());
				infoBuilder.append("\tIsTerminated : " + threadPool.isTerminated());
				infoBuilder.append("\tIsTerminating() : " + threadPool.isTerminating());

				logger.debug(infoBuilder.toString());
			} else {
				logger.debug("ExecutorService instance : " + executorService);
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		if (null != executorService) {
			printThreadPoolInfo();
			logger.info("ExecutorService will be shutdown.");
			executorService.shutdown();
			logger.info("Succeed to stopped ExecutorService.");
			printThreadPoolInfo();
		}
	}
}