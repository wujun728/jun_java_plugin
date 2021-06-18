package com.java1234.chap09.sec02;

public class Thread3 implements Runnable{

	private int baoZi=1;
	
	private String threadName;

	public Thread3(String threadName) {
		super();
		this.threadName = threadName;
	}

	@Override
	public synchronized void run() {
		while(baoZi<=10){
			System.out.println(threadName+" 吃第"+baoZi+"个包子");
			baoZi++;
		}
	}
	
	public static void main(String[] args) {
		Thread3 t1=new Thread3("超级张三线程");

		Thread t11=new Thread(t1);
		Thread t12=new Thread(t1);
		Thread t13=new Thread(t1);
		
		t11.start();
		t12.start();
		t13.start();
	}

}
