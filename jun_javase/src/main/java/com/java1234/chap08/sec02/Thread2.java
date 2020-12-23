package com.java1234.chap08.sec02;

public class Thread2 implements Runnable{

	private int baoZi=1;
	
	private String threadName;

	public Thread2(String threadName) {
		super();
		this.threadName = threadName;
	}

	@Override
	public synchronized void run() {
		while(baoZi<=10){
			System.out.println(threadName+" 吃第"+baoZi+"包子");
			baoZi++;
		}
	}
	
	public static void main(String[] args) {
		/*System.out.println("张三，李四一起吃包子，每人吃了10个");
		Thread2 t1=new Thread2("张三线程");
		Thread2 t2=new Thread2("李四线程");
		Thread t11=new Thread(t1);
		Thread t12=new Thread(t2);
		t11.start();
		t12.start();*/
		
		Thread2 t1=new Thread2("超级张三线程");
		Thread t11=new Thread(t1);
		Thread t12=new Thread(t1);
		Thread t13=new Thread(t1);
		// 实现资源共享
		t11.start();
		t12.start();
		t13.start();
		
	}

}
