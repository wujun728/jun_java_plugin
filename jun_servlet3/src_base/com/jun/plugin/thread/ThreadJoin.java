package com.jun.plugin.thread;

public class ThreadJoin {

	public static void main(String[] args) {
		System.err.println("主线程启动");
		BThread bt = new BThread();
		AThread at = new AThread(bt);
		bt.setAt(at);
		try {
			System.err.println("主线程启动线程B");
			bt.start();
			System.err.println("主线程休眠2秒");
			Thread.sleep(3000);
			System.err.println("主线程启动线程A");
			at.start();
			System.err.println("主线程调用了线程" + at.getName() + "的JOIN方法");
			at.join();
			System.err.println("主线调用线程A的JOIN方法后");
		} catch (Exception e) {
			System.out.println("Exception from main");
		}
		System.err.println("主线程结束");
	}

}

class BThread extends Thread {

	AThread at;

	public BThread() {
		super("[线程B] ");
	};

	public void setAt(AThread at) {
		this.at = at;
	}

	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " 启动.");
		try {
			for (int i = 0; i < 5; i++) {
				System.out.println(threadName + " loop at " + i);
				Thread.sleep(1000);
			}
			System.out.println("线程B调用了线程A的JOIN方法");
			at.join();
			System.out.println(threadName + " 结束");
		} catch (Exception e) {
			System.out.println("Exception from " + threadName + ".run");
		}
	}
}

class AThread extends Thread {
	BThread bt;

	public AThread(BThread bt) {
		super("[线程A] ");
		this.bt = bt;
		System.out.println(this.bt.hashCode());
	}

	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " 启动.");
		try {
			System.out.println(threadName + "休眠3秒");
			Thread.sleep(3000);
			System.out.println(threadName + "调用了" + this.bt.getName()
					+ "的JOIN方法");
			bt.join();
			System.out.println(threadName + " 结束");
		} catch (Exception e) {
			System.out.println("Exception from " + threadName + ".run");
		}
	}
}