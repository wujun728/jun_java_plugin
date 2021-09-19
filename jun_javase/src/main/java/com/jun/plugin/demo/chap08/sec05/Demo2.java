package com.jun.plugin.demo.chap08.sec05;

public class Demo2 implements Runnable{

	private int baoZi=10;
	
	/**
	 * ͬ������
	 */
	public synchronized void run() {
		// TODO Auto-generated method stub
		while(baoZi>0){
			System.out.println(Thread.currentThread().getName()+":���˵�"+baoZi+"������");
			baoZi--;
		}
	}
	
	public static void main(String[] args) {
		Demo2 demo1=new Demo2();
		new Thread(demo1,"����").start();
		new Thread(demo1,"����").start();
		new Thread(demo1,"����").start();
	}

}
