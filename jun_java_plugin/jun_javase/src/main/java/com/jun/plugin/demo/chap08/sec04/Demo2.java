package com.jun.plugin.demo.chap08.sec04;

public class Demo2 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			// ��ȡ��ǰ�߳�
			Thread t=Thread.currentThread();
			System.out.println(t.getName()+":"+i); // �����̵߳�����
		}
	}
	
	public static void main(String[] args) {
		Demo2 demo2=new Demo2();
		Thread t1=new Thread(demo2);
		System.out.println("t1�Ƿ�:"+t1.isAlive());
		t1.start();
		System.out.println("t1�Ƿ�:"+t1.isAlive());
	}
}
