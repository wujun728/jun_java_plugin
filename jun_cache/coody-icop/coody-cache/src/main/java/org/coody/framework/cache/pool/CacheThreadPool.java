package org.coody.framework.cache.pool;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class CacheThreadPool {

	public static final ScheduledThreadPoolExecutor  TASK_POOL = new ScheduledThreadPoolExecutor(30, new ThreadFactory() {
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r);
		}
	});
	
	
}
