package com.jun.plugin.demo.chap08.sec04;

public class Demo5 implements Runnable{

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(100);
				// ��ȡ��ǰ�߳�
				Thread t=Thread.currentThread();
				System.out.println(t.getName()+":"+i); // �����̵߳�����
				if(i==5){
					System.out.println("�߳����ã�");
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
		new Thread(demo1,"�߳�A").start();
		new Thread(demo1,"�߳�B").start();
	}

}
