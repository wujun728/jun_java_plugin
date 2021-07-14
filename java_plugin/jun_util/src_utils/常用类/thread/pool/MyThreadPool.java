package book.thread.pool;

import java.util.LinkedList;

/**
 * 线程池，继承ThreadGroup。
 * ThreadGroup用于处理一组线程的类，它是一种树状结构,他的下层节点还可以是ThreadGroup对象
 */
public class MyThreadPool extends ThreadGroup {

	/**	标志线程池是否开启	*/
	private boolean isAlive;
	/**	线程池中的任务队列	*/
	private LinkedList taskQueue;
	/**	线程池中的线程ID	*/
	private int threadID;
	/**	线程池ID	*/
	private static int threadPoolID;

	/**
	 *  创建新的线程池，numThreads是池中的线程数
	 */
	public MyThreadPool(int numThreads) {
		super("ThreadPool-" + (threadPoolID++));
		//设置为该线程池是的daemon属性为true，
		//表示当该线程池中所有线程都被销毁时，该线程池会自动被销毁
		super.setDaemon(true);
		this.isAlive = true;
		//新建一个任务队列
		this.taskQueue = new LinkedList();
		//启动numThreads个工作线程
		for (int i = 0; i < numThreads; i++) {
			new PooledThread().start();
		}
	}
	/**
	 * 添加新任务
	 */
	public synchronized void performTask(Task task) {
		if (!this.isAlive) {
			//	线程被关则抛出IllegalStateException异常
			throw new IllegalStateException();
		}
		if (task != null) {
			//将任务放到任务队列的尾部
			this.taskQueue.add(task);
			//通知工作线程取任务
			notify();
		}

	}

	/**
	 * 获取任务
	 */
	protected synchronized Task getTask() throws InterruptedException {
		//如果任务列表为空，而且线程池没有被关闭，则继续等待任务
		while (this.taskQueue.size() == 0) {
			if (!this.isAlive) {
				return null;
			}
			wait();
		}
		//取任务列表的第一个任务
		return (Task) this.taskQueue.removeFirst();
	}

	/**
	 * 关闭线程池，所有线程停止，不再执行任务
	 */
	public synchronized void close() {
		if (isAlive) {
			this.isAlive = false;
			//清除任务
			this.taskQueue.clear();
			//中止线程池中所有线程
			this.interrupt();
		}
	}

	/**
	 * 关闭线程池，并等待线程池中的所有任务被运行完。
	 * 但是不能接受新的任务。
	 */
	public void join() {
		//通知其他等待线程“该线程池已关闭”的消息
		synchronized (this) {
			isAlive = false;
			notifyAll();
		}
		// 等待所有线程完成
		// 首先建立一个新的线程数组。activeCount方法获取线程池中活动线程的估计数
		Thread[] threads = new Thread[this.activeCount()];
		// 将线程池中的活动线程拷贝到新创建的线程数组threads中。
		int count = this.enumerate(threads);
		for (int i = 0; i < count; i++) {
			try {
				// 等待线程运行结束
				threads[i].join();
			} catch (InterruptedException ex) {
			}
		}
	}

	/**
	 * 内部类，用于执行任务的工作线程
	 */
	private class PooledThread extends Thread {

		//构造方法
		public PooledThread() {
			//第一个参数为该线程所在的线程组对象，即当前线程池对象
			//第二个参数为线程名字
			super(MyThreadPool.this, "PooledThread-" + (threadID++));
		}

		public void run() {
			//如果该线程没有被中止
			while (!isInterrupted()) {

				// 获取任务
				Task task = null;
				try {
					task = getTask();
				} catch (InterruptedException ex) {
				}

				//只要线程池的任务列表不为空，getTask方法总能得到一个任务。
				//若getTask()返回null，则表示线程池中已经没有任务，而且线程池已被关闭。
				if (task == null) {
					return;
				}

				// 运行任务，吸收异常
				try {
					task.perform();
				} catch (Throwable t) {
					//当线程组中的线程有未被捕获的异常发生时，JVM就会去调用uncaughtException方法。
					uncaughtException(this, t);
				}
			}
		}
	}
}