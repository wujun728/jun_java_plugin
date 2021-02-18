package com.java1234.chap09.sec04;

public class Demo2 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			Thread thread=Thread.currentThread();
			System.out.println(thread.getName()+"："+i);
		}
	}
	
	public static void main(String[] args) {
		Demo2 demo1=new Demo2();
		Thread t1=new Thread(demo1);
		System.out.println("t1是否活动："+t1.isAlive());
		t1.start();
		System.out.println("t1是否活动："+t1.isAlive());
	
	}

}
