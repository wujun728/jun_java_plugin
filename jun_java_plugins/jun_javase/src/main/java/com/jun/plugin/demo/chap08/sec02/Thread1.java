package com.jun.plugin.demo.chap08.sec02;

public class Thread1 extends Thread{

	private int baoZi=1;
	
	private String threadName;

	public Thread1(String threadName) {
		super();
		this.threadName = threadName;
	}

	@Override
	public void run() {
		while(baoZi<=10){
			System.out.println(threadName+" �Ե�"+baoZi+"����");
			baoZi++;
		}
	}
	
	public static void main(String[] args) {
		System.out.println("����������һ��԰��ӣ�ÿ�˳���10��");
		Thread1 t1=new Thread1("�����߳�");
		Thread1 t2=new Thread1("�����߳�");
		t1.start();
		t2.start();
	}
	
	
}
