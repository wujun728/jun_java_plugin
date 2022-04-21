package com.jun.plugin.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁、同一线程可重新持锁进入
 */
public class LockReentrant {

	public static void main(String[] args) {

		final LockReentrant out = new LockReentrant();

		new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {

					out.print("abcdefghij");

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {

					out.print("0123456789");

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		}).start();

	}

	Lock lock = new ReentrantLock();

	public void print(String value) {

		char[] chars = value.toCharArray();

		lock.lock();

		for (char c : chars) {

			System.out.print(c);

		}

		System.out.println();

		lock.unlock();

	}

}
