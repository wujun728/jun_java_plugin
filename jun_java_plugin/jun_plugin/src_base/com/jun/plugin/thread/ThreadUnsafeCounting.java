package com.jun.plugin.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 不同步线程计数
 */
public class ThreadUnsafeCounting implements Runnable {

	public static int count = 1;

	private static int threadCount = 1;

	public static void add() {
		synchronized (ThreadUnsafeCounting.class) {
			count++;
		}
	}

	public static void main(String[] args) {

		System.out.println("begin");

		final int total = 999;

		List<Thread> threads = new ArrayList<>(total);

		for (int i = 1; i < total; i++) {
			threads.add(new Thread(new ThreadUnsafeCounting()));
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(7);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("count:" + ThreadUnsafeCounting.count);
				}
			}

		}).start();

		for (Thread thread : threads) {
			threadCount++;
			thread.start();
		}

		System.out.println("threadCount:" + threadCount);

	}

	@Override
	public void run() {

		// UnsafeCountingThread.count++;
		add();
	}

}
