package com.jun.plugin.demo.chap08.sec05;

public class Demo3 implements Runnable{

	private int baoZi=10;
	
	@Override
	public void run() {
		/**
		 * ͬ����
		 */
		synchronized (this) {
			while(baoZi>0){
				System.out.println(Thread.currentThread().getName()+":���˵�"+baoZi+"������");
				baoZi--;
			}			
		}
	}
	
	public static void main(String[] args) {
		Demo3 demo1=new Demo3();
		new Thread(demo1,"����").start();
		new Thread(demo1,"����").start();
		new Thread(demo1,"����").start();
	}

}
