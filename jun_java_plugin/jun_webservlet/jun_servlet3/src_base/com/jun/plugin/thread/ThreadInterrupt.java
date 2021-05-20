package com.jun.plugin.thread;

public class ThreadInterrupt {

	public static void main(String[] args) throws InterruptedException {
		MyThread mt = new MyThread();
		mt.start();
		System.out.println("zzz");
		Thread.sleep(2000);
		System.out.println("Y");
		mt.interrupt();
	}

}

class MyThread extends Thread {

	int a = 1;

	boolean cancelled = false;

	@Override
	public void run() {
		System.out.println("start");
		boolean state;
		while ((state = Thread.interrupted()) == false) {
			try {
				System.out.println(state);
				Thread.sleep(100);
				System.out.println(a++);
			} catch (InterruptedException e) {
				interrupt();
				System.err.println(Thread.currentThread().isInterrupted());
			}
		}
		System.out.println("stop");
	}

}
