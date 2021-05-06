package com.jun.plugin.thread;

public class ThreadWaitNotify {

	/* 测试线程循环+等待 */
	/* 主线程循环2次,子线程循环5次,如此交替共重复50次,总计350次 */

	public static void main(String[] args) {

		final ThreadWaitNotify twn = new ThreadWaitNotify();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					// System.out.println("主线程调度:[" + i + "]");
					try {
						twn.beginMain();
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
				for (int i = 1; i <= 50; i++) {
					// System.out.println("子线程调度:[" + i + "]");
					try {
						twn.beginSub();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}).start();

	}

	boolean isMain = true;

	static int count = 0;

	public synchronized void beginMain() throws InterruptedException {

		if (!isMain) {
			this.wait();
		}

		for (int i = 1; i <= 2; i++) {
			System.out.println("main:[" + i + "]");
			count++;
		}

		isMain = false;
		this.notify();

		System.out.println(count);

	}

	public synchronized void beginSub() throws InterruptedException {

		if (isMain) {
			this.wait();
		}

		for (int i = 1; i <= 5; i++) {
			System.out.println("sub:[" + i + "]");
			count++;
		}

		isMain = true;
		this.notify();

		System.out.println(count);

	}

}
