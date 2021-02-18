package com.jun.web.utils.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskPoolManager {
	
	private static TaskPoolManager tpm = new TaskPoolManager();
	
    private TaskPoolManager() {
    	
    }
    
	public static TaskPoolManager newInstance() { 
		return tpm;
	}

	private final static int CORE_POOL_SIZE = 4;
	
	private final static int MAX_POOL_SIZE = 100;
	
	private final static int KEEP_ALIVE_TIME = 0;
	
	private final static int WORK_QUEUE_SIZE = 100;
	
	private Queue<TaskEntity> taskQueue = new LinkedList<TaskEntity>();
	
	final Runnable accessBufferThread = new Runnable() {
		public void run() {
			if (hasMoreAcquire()) {
				TaskEntity msg = taskQueue.poll();
				Runnable task = new TaskRunner(msg);
				threadPool.execute(task);
			}
		}
	};
	
	final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			taskQueue.offer(((TaskRunner) r).getTask());
		}
	};
	
	final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
			CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(WORK_QUEUE_SIZE), this.handler);
	
	final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	final ScheduledFuture<?> taskHandler = scheduler.scheduleAtFixedRate(accessBufferThread, 0, 1, TimeUnit.SECONDS);


	private boolean hasMoreAcquire() {
		return !taskQueue.isEmpty();
	}

	public void addTask(TaskEntity msg) {
		Runnable task = new TaskRunner(msg);
		threadPool.execute(task);
	}
	
	public void addTasks(List<TaskEntity> msgList) {
		Runnable task = null;
		for(TaskEntity msg : msgList){
			task = new TaskRunner(msg);
			threadPool.execute(task);
		}
	}
}
