package com.jun.plugin.thread;

import java.util.concurrent.CountDownLatch;

/**
 * create at 11-9-17
 * 
 * @author KETQI
 * @category CountDownLatch主要起倒计时计数器作用，它主要有两个方法await()和countDown()。
 *           一旦某个线程调用await()方法，那么该线程就会阻塞，等待CountDownLatch计数器倒计时归零，
 *           需要注意的是尽管线程调用await()方法后会阻塞，
 *           但是CountDownLatch允许别的线程调用countDown()方法，将计数器减一。
 *           也就是说调用计时器的线程阻塞后，可以利用别的线程控制调用线程何时从新开始运行。
 *           <p/>
 *           该demo主要想要做的事就是：在主线程中创建N个子线程，让支线程等待主线程将开关计数器startSignal打开。
 *           而当主线程打开startSignal开关后，主线程要等待计数器doneSignal归零，
 *           而doneSignal计数器归零依赖于每个支线程为主线程的计数器减一。
 *           所以当主线程打开开关后，支线程才能运行完毕，而只有支线程全部运行完毕，才能打开主线程的计数器。 这样整个程序才能走完
 */
public class CountDownLatchDemo {
	public static final int N = 5;

	public static void main(String[] args) throws InterruptedException {
		// 用于向工作线程发送启动信号,由主线程调用
		CountDownLatch startSignal = new CountDownLatch(1);
		// 用于等待工作线程的结束信号,由子线程调用
		CountDownLatch doneSignal = new CountDownLatch(N);
		// 创建启动线程
		System.out
				.println("开始创建并运行分支线程，且分支线程启动startSignal计数器，等待主线程将startSignal计数器打开");
		for (int i = 0; i < N; i++) {
			new Thread(new LatchWorker(startSignal, doneSignal), "t" + i)
					.start();
		}

		// 主线程，递减开始计数器，让所有线程开始工作
		System.out.println("主线程" + Thread.currentThread().getName()
				+ "将startSignal计数器打开");
		startSignal.countDown();
		// 主线程阻塞，等待所有线程完成
		System.out.println("主线程" + Thread.currentThread().getName()
				+ "开始倒计时5个数");
		doneSignal.await();
		/**
		 * 为什么说运行到下一句，所有线程就全部运行完毕了呢。 因为主线程要倒计时5个数， 而产生的5个支线程在运行完毕前会将主线程的计数器减一，
		 * 所以如果所有支线程运行完毕了 ，主线程才能继续运行主线程的最后一个打印程序
		 */
		System.out.println("所有线程运行完毕");
	}
}

class LatchWorker implements Runnable {
	// 用于等待启动信号
	private final CountDownLatch startSignal;
	// 用于发送结束信号
	private final CountDownLatch doneSignal;

	LatchWorker(CountDownLatch startSignal, CountDownLatch doneSignal) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
	}

	public void run() {
		try {
			// 一旦调用await()方法，该线程就会开始阻塞。知道计数器startSignal为0
			System.out.println(Thread.currentThread().getName()
					+ " 开始调用await()方法，等待计数器startSignal被主线程打开");
			startSignal.await();
			doWork();
			System.out
					.println(Thread.currentThread().getName() + " 将主线程的计数器减一");
			doneSignal.countDown();// 发送完成信号
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	void doWork() {
		System.out.println(Thread.currentThread().getName()
				+ " 的计数器被打开，分支线程开始运行");
		try {
			Thread.sleep((long) Math.random() * 10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}