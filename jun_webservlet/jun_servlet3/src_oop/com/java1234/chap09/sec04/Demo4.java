package com.java1234.chap09.sec04;

public class Demo4 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(1);
				Thread thread=Thread.currentThread();
				System.out.println(thread.getName()+"："+i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Demo4 demo1=new Demo4();
		Thread t1=new Thread(demo1,"线程A");
		Thread t2=new Thread(demo1,"线程B");
		Thread t3=new Thread(demo1,"线程C");
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MIN_PRIORITY);
		t3.setPriority(Thread.NORM_PRIORITY);
		t1.start();
		t2.start();
		t3.start();
	}

}
