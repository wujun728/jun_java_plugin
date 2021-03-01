package com.java1234.chap08.sec04;

public class Demo4 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			// 获取当前线程
			Thread t=Thread.currentThread();
			System.out.println(t.getName()+":"+i); // 返回线程的名称
		}
	}
	
	public static void main(String[] args) {
		Demo4 demo4=new Demo4();
		Thread t1=new Thread(demo4,"线程A");
		Thread t2=new Thread(demo4,"线程B");
		Thread t3=new Thread(demo4,"线程C");
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MIN_PRIORITY);
		t3.setPriority(Thread.NORM_PRIORITY);
		t1.start();
		t2.start();
		t3.start();
	}

}
