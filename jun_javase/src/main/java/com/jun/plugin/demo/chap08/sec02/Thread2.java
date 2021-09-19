package com.jun.plugin.demo.chap08.sec02;

public class Thread2 implements Runnable{

	private int baoZi=1;
	
	private String threadName;

	public Thread2(String threadName) {
		super();
		this.threadName = threadName;
	}

	@Override
	public synchronized void run() {
		while(baoZi<=10){
			System.out.println(threadName+" �Ե�"+baoZi+"����");
			baoZi++;
		}
	}
	
	public static void main(String[] args) {
		/*System.out.println("����������һ��԰��ӣ�ÿ�˳���10��");
		Thread2 t1=new Thread2("�����߳�");
		Thread2 t2=new Thread2("�����߳�");
		Thread t11=new Thread(t1);
		Thread t12=new Thread(t2);
		t11.start();
		t12.start();*/
		
		Thread2 t1=new Thread2("���������߳�");
		Thread t11=new Thread(t1);
		Thread t12=new Thread(t1);
		Thread t13=new Thread(t1);
		// ʵ����Դ����
		t11.start();
		t12.start();
		t13.start();
		
	}

}
