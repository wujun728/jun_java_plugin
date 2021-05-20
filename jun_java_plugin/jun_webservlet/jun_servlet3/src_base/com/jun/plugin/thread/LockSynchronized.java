package com.jun.plugin.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对同一个对象
 */
public class LockSynchronized implements Runnable {

	public static void main(String[] args) {

		LockSynchronized ls = new LockSynchronized();

		new Thread(ls).start();
		new Thread(ls).start();

	}

	private AtomicInteger ai = new AtomicInteger(0);

	@Override
	public void run() {

		try {
			if (ai.getAndIncrement() == 0) {
				doOne();
				doAsync();
				doOtherOne();
			} else {
				doTwo();
				doAsync();
				doOtherTwo();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized void doOne() throws InterruptedException {

		Thread.sleep(4000);
		System.out.println("do one");

	}

	public synchronized void doTwo() throws InterruptedException {

		Thread.sleep(4000);
		System.out.println("do two");

	}

	public void doOtherOne() throws InterruptedException {

		synchronized (this) {

			Thread.sleep(4000);
			System.out.println("do other one");

		}

	}

	public void doOtherTwo() throws InterruptedException {

		synchronized (this) {

			Thread.sleep(4000);
			System.out.println("do other two");

		}

	}

	public void doAsync() throws InterruptedException {

		Thread.sleep(4000);
		System.out.println("do async");

	}

}
