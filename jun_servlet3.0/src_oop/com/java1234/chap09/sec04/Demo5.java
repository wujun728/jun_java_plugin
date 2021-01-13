package com.java1234.chap09.sec04;

public class Demo5 implements Runnable{

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(1);
				Thread thread=Thread.currentThread();
				System.out.println(thread.getName()+"："+i);
				if(i==5){
					System.out.println("线程礼让：");
					thread.yield();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Demo5 demo1=new Demo5();
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
