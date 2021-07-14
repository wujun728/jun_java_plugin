package book.thread;

import java.util.Date;

public class DefineThread {
	/**
	 * 通过继承java.lang.Thread类定义线程
	 */
	class ThreadA extends Thread{
		/**	线程被运行的时刻	*/
		private Date runDate;
		/**
		 * 当线程被运行时调用此方法
		 */
		public void run(){
			System.out.println("ThreadA begin,");
			this.runDate = new Date();
			System.out.println("ThreadA end.");
		}
	}
	
	/**
	 * 通过实现java.lang.Runnable接口定义线程
	 */
	class ThreadB implements Runnable{
		/**	线程被运行的时刻	*/
		private Date runDate;
		public void run(){
			System.out.println("ThreadB begin,");
			this.runDate = new Date();
			System.out.println("ThreadB end.");
		}
	}
	/**
	 * 启动一个ThreadA线程
	 */
	public void startA(){
		Thread threadA = new ThreadA();
		//调用Thread的start方法启动线程
		threadA.start();
	}
	/**
	 * 启动一个ThreadB线程
	 */
	public void startB(){
		Runnable tb = new ThreadB();
		//用Runnable对象构造线程
		Thread threadB = new Thread(tb);
		threadB.start();
	}
	
	public static void main(String[] args) {
		DefineThread test = new DefineThread();
		//线程的运行具有不确定性，先启动的线程不一定先运行，取决于虚拟机。
		test.startA();
		test.startB();
	}
}
