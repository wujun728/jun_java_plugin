package com.jun.plugin.demo.chap08.sec04;

public class Demo4 implements Runnable{

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
		Demo4 demo4=new Demo4();
		Thread t1=new Thread(demo4,"�߳�A");
		Thread t2=new Thread(demo4,"�߳�B");
		Thread t3=new Thread(demo4,"�߳�C");
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MIN_PRIORITY);
		t3.setPriority(Thread.NORM_PRIORITY);
		t1.start();
		t2.start();
		t3.start();
	}

}
