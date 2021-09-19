package com.jun.plugin.commons.util.thread;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

public class ThreadPool {
	private static final java.util.Map<String, ExecutorService> executorServiceMap = new java.util.HashMap<String, ExecutorService>();

	/*****
	 * 通过名字得到线程池
	 * 
	 * @param poolname
	 * @return
	 */
	public static final ExecutorService getThreadPoolByName(String poolname,
			Properties properties) {
		if (StringUtils.isEmpty(poolname)) {
			throw new IllegalArgumentException("需要传入线程池名称");
		}
		synchronized (executorServiceMap) {
			if (executorServiceMap.containsKey(poolname)) {
				return executorServiceMap.get(poolname);
			} else {
				String preStr = "thread.pool." + poolname + ".";
				String poolNameStr = properties
						.getProperty(preStr + "poolname");
				if (StringUtils.isEmpty(poolNameStr)) {
					throw new IllegalArgumentException("你请求的池没有定义");
				}
				String coreSize = properties.getProperty(preStr + "coreSize");
				String maxSize = properties.getProperty(preStr + "maxSize");
				String queueSize = properties.getProperty(preStr + "queueSize");
				String keepAliveTime = properties.getProperty(preStr
						+ "keepAliveTime");
				String unit = properties.getProperty(preStr + "unit");
				if (StringUtils.isEmpty(coreSize)
						|| StringUtils.isEmpty(maxSize)
						|| StringUtils.isEmpty(queueSize)) {
					throw new IllegalArgumentException("池核心数、最大数、队列数必须定义");
				}
				int coreSizeInt = Integer.parseInt(coreSize);
				int maxSizeInt = Integer.parseInt(maxSize);
				int queueSizeInt = Integer.parseInt(queueSize);
				int keepAliveTimeInt = StringUtils.isEmpty(keepAliveTime) ? 10
						: Integer.parseInt(keepAliveTime);
				TimeUnit unitT = StringUtils.isEmpty(unit) ? TimeUnit.SECONDS
						: getTimeUnit(unit);
				BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(
						queueSizeInt);
				ThreadPoolExecutor executor = new ThreadPoolExecutor(
						coreSizeInt, maxSizeInt, keepAliveTimeInt, unitT,
						queue, new RejectedExecutionForLog());
				executorServiceMap.put(poolname, executor);
				return executor;
			}
		}
	}

	/***
	 * 得到默认的线程池
	 * 
	 * @return
	 */
	public static final ExecutorService getDefaultPool(Properties properties) {
		return getThreadPoolByName("default", properties);
	}

	private static TimeUnit getTimeUnit(String TimeUnitStr) {
		if (StringUtils.isEmpty(TimeUnitStr)) {
			return null;
		}
		for (TimeUnit timeUnit : TimeUnit.values()) {
			if (timeUnit.name().equals(TimeUnitStr)) {
				return timeUnit;
			}
		}
		return null;
	}

}
