package org.coody.framework.task.threadpool;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class TaskThreadPool {

	public static final ScheduledThreadPoolExecutor  TASK_POOL = new ScheduledThreadPoolExecutor(30, new ThreadFactory() {
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r);
		}
	});
	
	
}
