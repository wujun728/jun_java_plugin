package book.thread;

/**
 * 线程的结合。
 * 当一个线程需要等待另一个线程结束时，叫做线程的结合。
 */
public class JoinThread {
	/**	自定义线程类 */
	static class ThreadA extends Thread{
		//线程的ID
		private int ID = 0;
		//线程运行时循环的次数
		private int whileTimes = 0;
		public ThreadA(int id, int times){
			this.ID = id;
			this.whileTimes = times;
		}
		public void run(){
			System.out.println("ThreadA" + this.ID + " begin!");
			int i=0; 
			try {
				//连续循环whileTimes次
				while (i < this.whileTimes){
					System.out.println("ThreadA-" + this.ID + ": " + i++);
					//sleep方法将当前线程休眠。
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
			}

			System.out.println("ThreadA" + this.ID + " end!");
		}
	}
	public static void main(String[] args) {
		//新建4个线程对象
		Thread thread1 = new ThreadA(1, 3);
		Thread thread2 = new ThreadA(2, 2);
		Thread thread3 = new ThreadA(3, 2);
		Thread thread4 = new ThreadA(4, 4);
		//启动所有线程
		System.out.println("Main method begin. To start 4 threads!");
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		//等待所有线程运行结束
		try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//此时所有线程都运行结束
		System.out.println("Main method end! All 4 threads are ended");
	}
}
