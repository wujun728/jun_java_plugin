package com.java1234.chap09.sec04;

public class Demo3 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(1000);
				Thread thread=Thread.currentThread();
				System.out.println(thread.getName()+"£º"+i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Demo3 demo1=new Demo3();
		Thread t1=new Thread(demo1);
		t1.start();
	}

}
