package com.java1234.chap09.sec05;

public class Thread5 implements Runnable{

	private int baoZi=1;
	
	private String threadName;

	public Thread5(String threadName) {
		super();
		this.threadName = threadName;
	}

	@Override
	public  void run() {
			while(baoZi<=10){
				synchronized (this) {
					if(baoZi<=10){
						System.out.println(Thread.currentThread().getName()+" 吃第"+baoZi+"个包子");						
					}
					baoZi++;
				}
			}
			
	}
	
	public static void main(String[] args) {
		Thread5 t1=new Thread5("超级张三线程");

		Thread t11=new Thread(t1,"A");
		Thread t12=new Thread(t1,"B");
		Thread t13=new Thread(t1,"C");
		
		t11.start();
		t12.start();
		t13.start();
	}

}
