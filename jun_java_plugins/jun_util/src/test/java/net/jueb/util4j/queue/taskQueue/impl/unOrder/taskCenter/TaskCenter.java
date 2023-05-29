package net.jueb.util4j.queue.taskQueue.impl.unOrder.taskCenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.NullArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.jueb.util4j.queue.taskQueue.Task;
import net.jueb.util4j.queue.taskQueue.TaskConvert;
import net.jueb.util4j.queue.taskQueue.TaskQueueExecutor;
import net.jueb.util4j.queue.taskQueue.impl.DefaultTaskConvert;

/**
 * 任务管理中心。管理多线程执行，一般的，常规任务执行都应当使用任务中心执行。
 * 无序任务执行
 */
public final class TaskCenter implements TaskQueueExecutor {

	private final static Logger logger = LogManager.getLogger(TaskCenter.class.getName());

	private ExecutorService mainExecutor;
	private ScheduledExecutorService scheduledExecutor;

	private ScheduledFuture<?> monitoring;
	private Vector<ScheduledFuture<?>> scheduledFutureList;
	private ConcurrentHashMap<String, Timer> timers;

	// 滑动窗
	private TaskSlidingWindow slidingWindow;

	private volatile int maxThreads = 200;
	private final int corePoolSize = 4;

	private AtomicInteger threadCounts;
	private Queue<Runnable> taskQueue;

	private Queue<SpinningTask> taskPool;
	private final String name;
	
	public TaskCenter(String queueName)
	{
		this.name=queueName;
		this.mainExecutor = Executors.newCachedThreadPool();
		this.scheduledExecutor = Executors.newScheduledThreadPool(this.corePoolSize);
		this.taskQueue = new LinkedList<Runnable>();
		this.taskPool = new LinkedList<SpinningTask>();
		this.threadCounts = new AtomicInteger(0);
		this.scheduledFutureList = new Vector<ScheduledFuture<?>>();
		this.timers = new ConcurrentHashMap<String, Timer>();
		this.slidingWindow = new TaskSlidingWindow(this);
		this.slidingWindow.start();
		this.monitoring = this.scheduledExecutor.scheduleAtFixedRate(new MonitoringTask(), 5, 60, TimeUnit.MINUTES);
	}

	public void setMaxThread(int max) {
		this.maxThreads = max;
	}

	public int getMaxThread() {
		return this.maxThreads;
	}

	public int snapshotThreadNum() {
		return this.threadCounts.get();
	}

	public int snapshotQueueSize() {
		synchronized (this.taskQueue) {
			return this.taskQueue.size();
		}
	}

	public int snapshotScheduleNum() {
		return this.scheduledFutureList.size() + this.timers.size();
	}

	/** 执行并发任务。
	 */
	@Override
	public void execute(Runnable task) {
		synchronized (this.taskQueue) {
			this.taskQueue.offer(task);
		}
		try {
			if (this.threadCounts.get() < this.maxThreads) {
				this.mainExecutor.execute(this.borrowSpinningTask());
			}
		} catch (RejectedExecutionException e) {
			logger.error("#execute", e);
		}
	}
	
	public void executes(List<Runnable> tasks) {
		synchronized (this.taskQueue) {
			this.taskQueue.addAll(tasks);
		}
		try {
			if (this.threadCounts.get() < this.maxThreads) {
				this.mainExecutor.execute(this.borrowSpinningTask());
			}
		} catch (RejectedExecutionException e) {
			logger.error("#execute", e);
		}
	}

	/**
	 * 使用滑动窗执行任务。
	 * @param task
	 */
	public void executeWithSlidingWindow(Runnable task) {
		this.slidingWindow.push(task);
	}

	/**
	 * 返回滑动窗当前队列长度。
	 * @return
	 */
	public int getWindowQueueSize() {
		return this.slidingWindow.getQueueSize();
	}

	/**
	 * 设置滑动窗每秒任务数。
	 * @param tasksPerSecond
	 */
	public int setWindowTPS(int tasksPerSecond) {
		return this.slidingWindow.setTPS(tasksPerSecond);
	}

	/** 执行定时任务。
	 */
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		ScheduledFuture<?> sf = this.scheduledExecutor.scheduleWithFixedDelay(command, initialDelay, delay, unit);
		this.scheduledFutureList.add(sf);
		return sf;
	}

	/** 执行定时任务。
	 */
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		ScheduledFuture<?> sf = this.scheduledExecutor.scheduleAtFixedRate(command, initialDelay, period, unit);
		this.scheduledFutureList.add(sf);
		return sf;
	}

	/** 一次性执行定时任务。
	 */
	public ScheduledFuture<?> schedule(Runnable command, long initialDelay, TimeUnit unit) {
		ScheduledFuture<?> sf = this.scheduledExecutor.schedule(command, initialDelay, unit);
		this.scheduledFutureList.add(sf);
		return sf;
	}

	/**
	 * 执行定时任务。
	 * @param name 任务名称
	 * @param task 待执行任务
	 * @param firstTime 首次执行时间
	 * @param period 以毫秒为单位的执行周期
	 */
	public void schedule(String name, Runnable task, Date firstTime, long period) {
		if (this.timers.containsKey(name)) {
			this.unschedule(name);
		}

		Timer timer = new Timer();
		timer.schedule(new TimerTaskWrapper(task), firstTime, period);
		this.timers.put(name, timer);
	}

	/**
	 * 取消定时任务。
	 * @param name 任务名称
	 */
	public void unschedule(String name) {
		Timer timer = this.timers.remove(name);
		if (null != timer) {
			timer.cancel();
		}
	}

	public void close() {
		System.out.println("TaskCenter closing");
		this.monitoring.cancel(true);

		// 停止所有定时任务
		for (ScheduledFuture<?> sf : this.scheduledFutureList) {
			if (!sf.isCancelled() || !sf.isDone()) {
				sf.cancel(true);
			}
		}
		this.scheduledFutureList.clear();

		Iterator<Timer> iter = this.timers.values().iterator();
		while (iter.hasNext()) {
			Timer timer = iter.next();
			timer.cancel();
		}
		this.timers.clear();

		// 关闭滑动窗
		this.slidingWindow.stop();

		// 关闭线程池
		this.mainExecutor.shutdown();
		this.scheduledExecutor.shutdown();
		System.out.println("TaskCenter closed");
	}

	private SpinningTask borrowSpinningTask() {
		synchronized (this.taskPool) {
			if (this.taskPool.isEmpty()) {
				return new SpinningTask();
			} else {
				return this.taskPool.poll();
			}
		}
	}

	private void returnSpinningTask(SpinningTask st) {
		synchronized (this.taskPool) {
			this.taskPool.offer(st);
		}
	}

	/**
	 * 消费队列任务。
	 */
	private class SpinningTask implements Runnable {

		protected SpinningTask() {
		}

		@Override
		public void run() {
			// 增加计数
			threadCounts.incrementAndGet();

			do {
				Runnable task = null;
				synchronized (taskQueue) {
					task = taskQueue.poll();
				}

				if (null != task) {
					task.run();
				}
			} while (!taskQueue.isEmpty());

			// 减少计数
			threadCounts.decrementAndGet();

			// 返回任务
			returnSpinningTask(this);
		}
	}

	/**
	 * 监视任务。
	 */
	private class MonitoringTask implements Runnable {

		protected MonitoringTask() {
		}

		@Override
		public void run() {
			Iterator<ScheduledFuture<?>> iter = scheduledFutureList.iterator();
			while (iter.hasNext()) {
				ScheduledFuture<?> sf = iter.next();
				if (sf.isCancelled() || sf.isDone()) {
					// 删除已经结束的任务
					iter.remove();
				}
			}
		}
	}

	/**
	 * 对 TimerTask 的简单封装。
	 */
	private class TimerTaskWrapper extends TimerTask {

		private Runnable task;

		protected TimerTaskWrapper(Runnable task) {
			super();
			this.task = task;
		}

		@Override
		public void run() {
			this.task.run();
		}
	}

	@Override
	public String getQueueName() {
		return name;
	}

	@Override
	public void execute(Task task) {
		execute((Runnable)task);
	}

	@Override
	public void execute(List<Task> tasks) {
		ArrayList<Runnable> list=new ArrayList<Runnable>();
		for(Task t:tasks)
		{
			list.add(t);
		}
		executes(list);
	}

	public static final TaskConvert DEFAULT_TASK_CONVERT=new DefaultTaskConvert();
   	private TaskConvert taskConvert=DEFAULT_TASK_CONVERT;
   	
   	@Override
   	public TaskConvert getTaskConvert() {
   		return taskConvert;
   	}
   	
   	@Override
   	public void setTaskConvert(TaskConvert taskConvert) {
   		if(taskConvert==null)
   		{
   			throw new NullArgumentException("taskConvert is null");
   		}
   		this.taskConvert=taskConvert;
   	}
}
