package com.java1234.chap08.sec04;

public class Demo2 implements Runnable{

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
		Demo2 demo2=new Demo2();
		Thread t1=new Thread(demo2);
		System.out.println("t1是否活动:"+t1.isAlive());
		t1.start();
		System.out.println("t1是否活动:"+t1.isAlive());
	}
}
