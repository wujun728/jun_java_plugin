package com.java1234.chap08.sec05;

public class Demo2 implements Runnable{

	private int baoZi=10;
	
	/**
	 * 同步方法
	 */
	public synchronized void run() {
		// TODO Auto-generated method stub
		while(baoZi>0){
			System.out.println(Thread.currentThread().getName()+":吃了第"+baoZi+"个包子");
			baoZi--;
		}
	}
	
	public static void main(String[] args) {
		Demo2 demo1=new Demo2();
		new Thread(demo1,"张三").start();
		new Thread(demo1,"李四").start();
		new Thread(demo1,"王五").start();
	}

}
