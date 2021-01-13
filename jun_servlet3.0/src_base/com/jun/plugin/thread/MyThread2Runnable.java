package com.jun.plugin.thread;

public class MyThread2Runnable implements Runnable{

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		for(int i= 0;i<1000;i++){
			System.out.println("thread:---"+i);
		}
	}

	public static void main(String[] args) {
		MyThread2Runnable m = new MyThread2Runnable();
		Thread t = new Thread(m);
		t.start();
		for(int i= 0;i<1000;i++){
			System.out.println("main:-------------------------------"+i);
		}
	}
}
