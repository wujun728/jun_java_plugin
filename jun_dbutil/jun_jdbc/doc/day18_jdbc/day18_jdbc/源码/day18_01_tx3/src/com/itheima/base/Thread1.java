package com.itheima.base;

public class Thread1 extends Thread {
	private ThreadLocal tl;
	public Thread1(ThreadLocal tl){
		this.tl = tl;
	}
	public void run() {
		System.out.println("T1:"+tl.get());
	}
	
}
