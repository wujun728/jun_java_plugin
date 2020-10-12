package com.ky26.object.test2;

public class ThreadTest2  {
	public static void main(String[] args) {
		ThreadDemo1 t1=new ThreadDemo1();
		ThreadDemo2 t2=new ThreadDemo2();
		ThreadDemo3 t3=new ThreadDemo3();
		t1.start();
		t2.start();
		t3.start();
		
	}
}
class ThreadDemo1 extends Thread{
	int a=10;
	public void run(){
		while(a<15){
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			for(int i=0;i<10;i++){
				System.out.println("-----"+i);
			}
			a++;
		}
	}
}
class ThreadDemo2 extends Thread{
	public void run(){
		for(int i=0;i<10;i++){
			System.out.println("-----a"+i);
		}
	}
}
class ThreadDemo3 extends Thread{
	public void run(){
		for(int i=0;i<10;i++){
			System.out.println("-----b"+i);
		}
	}
}

class JoinTest{
	int a=10;
	Thread ThreadA=new Thread(new Runnable(){
		public void run(){
			while(a<15){
				try{
					ThreadA.sleep(1000);
					ThreadB.join();
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				for(int i=0;i<10;i++){
					System.out.println("-----"+i);
				}
				a++;
			}
		}
	});
//	ThreadA.start();
	Thread ThreadB=new Thread(new Runnable(){
		public void run(){
			while(a<15){
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				for(int i=0;i<10;i++){
					System.out.println("-----"+i);
				}
				a++;
			}
		}
	});
}
