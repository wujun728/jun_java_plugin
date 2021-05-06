package com.java1234.chap09.sec04;

public class Demo1 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			Thread thread=Thread.currentThread();
			System.out.println(thread.getName()+"£º"+i);
		}
	}
	
	public static void main(String[] args) {
		Demo1 demo1=new Demo1();
		new Thread(demo1).start();
		new Thread(demo1).start();
		new Thread(demo1,"Ïß³Ì3").start();
	}

}
