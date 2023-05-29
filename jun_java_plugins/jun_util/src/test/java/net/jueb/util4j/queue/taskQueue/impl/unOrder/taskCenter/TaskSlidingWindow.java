package net.jueb.util4j.queue.taskQueue.impl.unOrder.taskCenter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 任务滑窗。
 */
public final class TaskSlidingWindow {

	private final static Logger logger = LogManager.getLogger(TaskSlidingWindow.class.getName());

	private Executor executor;

	// 每秒任务数
	private volatile int tasksPerSecond = 200;
	// 守护线程休眠时间
	private volatile long sleepTime = 4;

	private Queue<Runnable> taskList;
	private byte[] monitor;

	private WindowDaemon daemon;

	private boolean readOnly;

	public TaskSlidingWindow(Executor executor) {
		this.executor = executor;
		this.taskList = new LinkedList<Runnable>();
		this.monitor = new byte[0];
		this.readOnly = false;
	}

	public void start() {
		this.daemon = new WindowDaemon();
		this.daemon.setDaemon(false);
		this.daemon.start();
	}

	public void stop() {
		this.readOnly = true;

		while (!this.taskList.isEmpty()) {
			try {
				Thread.sleep(1000);
				System.out.println("have task will to do , size : " + this.taskList.size());
			} catch (InterruptedException e) {
			}
		}

		this.daemon.kill();
	}

	public void push(Runnable task) {
		if (this.readOnly) {
			return;
		}

		synchronized (this.taskList) {
			this.taskList.offer(task);
		}

		synchronized (this.monitor) {
			this.monitor.notifyAll();
		}
	}

	public int getQueueSize() {
		synchronized (this.taskList) {
			return this.taskList.size();
		}
	}

	public int setTPS(int value) {
		if (value > 1000) {
			return this.tasksPerSecond;
		}

		this.tasksPerSecond = value;
		this.sleepTime = ((int) Math.ceil(1000f / (float) value)) - 1;
		if (this.sleepTime < 0) {
			this.sleepTime = 0;
		}
		return this.tasksPerSecond;
	}

	public int getTPS() {
		return this.tasksPerSecond;
	}

	/**
	 * 滑动窗守护线程。
	 */
	protected class WindowDaemon extends Thread {

		private boolean stopped = false;

		protected WindowDaemon() {
			super("TaskSlidingWindowDaemon");
		}

		@Override
		public void run() {
			while (!this.stopped) {
				while (!taskList.isEmpty()) {
					Runnable task = null;
					synchronized (taskList) {
						task = taskList.poll();
					}

					if (null != task) {
						executor.execute(task);

						if (sleepTime > 0) {
							try {
								Thread.sleep(sleepTime);
							} catch (InterruptedException e) {
								logger.error("WindowDaemon#run", e);
							}
						}
					}
				}

				// 让步
				Thread.yield();

				synchronized (monitor) {
					try {
						monitor.wait();
					} catch (InterruptedException e) {
						TaskSlidingWindow.logger.error("WindowDaemon#run", e);

						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
						}
					}
				}
			}
		}

		public void kill() {
			this.stopped = true;
			synchronized (monitor) {
				monitor.notifyAll();
			}
		}
	}
}
