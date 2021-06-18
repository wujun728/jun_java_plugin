package com.java1234.chap08.sec04;

public class Demo5 implements Runnable{

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(100);
				// 获取当前线程
				Thread t=Thread.currentThread();
				System.out.println(t.getName()+":"+i); // 返回线程的名称
				if(i==5){
					System.out.println("线程礼让：");
					Thread.currentThread().yield();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Demo5 demo1=new Demo5();
		new Thread(demo1,"线程A").start();
		new Thread(demo1,"线程B").start();
	}

}
