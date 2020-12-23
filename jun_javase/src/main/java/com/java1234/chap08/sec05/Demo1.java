package com.java1234.chap08.sec05;

public class Demo1 implements Runnable{

	private int baoZi=10;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(baoZi>0){
			System.out.println(Thread.currentThread().getName()+":吃了第"+baoZi+"个包子");
			baoZi--;
		}
	}
	
	public static void main(String[] args) {
		Demo1 demo1=new Demo1();
		new Thread(demo1,"张三").start();
		new Thread(demo1,"李四").start();
		new Thread(demo1,"王五").start();
	}

}
