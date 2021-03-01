package com.ky26.object.test2;

public class ThreadTest{
	public static void main(String[] args) {
		ThreadDemo t1=new ThreadDemo();
		ThreadDemo t2=new ThreadDemo();
		t1.start();
//		t2.start();
		for(int i=0;i<10;i++){
			int[] arr=new int[3];
			//System.out.println(arr[9]);
			System.out.println(i+Thread.currentThread().getName());
		}
	}
}

class ThreadDemo extends Thread{
//	private String name="王麻子";
//	ThreadDemo(String name){
//		this.name=name;
//		super(name);
//	}
	public void run(){
		int[] arr=new int[3];
		//System.out.println(arr[3]);执行异常的线程不会影响到其他线程，如当前线程和main线程
		for(int i=0;i<10;i++){
			System.out.println(getName()+"-------"+i+"-------"+Thread.currentThread().getName());
		}
	}
}

