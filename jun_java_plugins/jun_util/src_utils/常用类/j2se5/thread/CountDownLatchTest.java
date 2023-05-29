package book.j2se5.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch是个计数器，它有一个初始数，
 * 等待这个计数器的线程必须等到计数器倒数到零时才可继续。
 */
public class CountDownLatchTest {

	/**
	 * 初始化组件的线程
	 */
	public static class ComponentThread implements Runnable {
		// 计数器
		CountDownLatch latch;
		// 组件ID
		int ID;

		// 构造方法
		public ComponentThread(CountDownLatch latch, int ID) {
			this.latch = latch;
			this.ID = ID;
		}

		public void run() {
			// 初始化组件
			System.out.println("Initializing component " + ID);
			try {
				Thread.sleep(500 * ID);
			} catch (InterruptedException e) {
			}
			System.out.println("Component " + ID + " initialized!");
			//将计数器减一
			latch.countDown();
		}
	}

	/**
	 * 启动服务器
	 */
	public static void startServer() throws Exception {
		System.out.println("Server is starting.");
		//初始化一个初始值为3的CountDownLatch
		CountDownLatch latch = new CountDownLatch(3);
		//起3个线程分别去启动3个组件
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(new ComponentThread(latch, 1));
		service.submit(new ComponentThread(latch, 2));
		service.submit(new ComponentThread(latch, 3));
		service.shutdown();

		//等待3个组件的初始化工作都完成
		latch.await();

		//当所需的三个组件都完成时，Server就可继续了
		System.out.println("Server is up!");
	}

	public static void main(String[] args) throws Exception {
		CountDownLatchTest.startServer();
	}
}