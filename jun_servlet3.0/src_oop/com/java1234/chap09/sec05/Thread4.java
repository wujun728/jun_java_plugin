package com.java1234.chap09.sec05;

public class Thread4 implements Runnable{

	private int baoZi=1;
	
	private String threadName;

	public Thread4(String threadName) {
		super();
		this.threadName = threadName;
	}

	@Override
	public synchronized void run() {
		synchronized (this) {
			while(baoZi<=10){
				System.out.println(threadName+" 吃第"+baoZi+"个包子");
				baoZi++;
			}
			
		}
	}
	
	public static void main(String[] args) {
		Thread4 t1=new Thread4("超级张三线程");

		Thread t11=new Thread(t1);
		Thread t12=new Thread(t1);
		Thread t13=new Thread(t1);
		
		t11.start();
		t12.start();
		t13.start();
	}

}
