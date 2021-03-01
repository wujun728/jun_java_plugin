/*package com.ky26.object.test2;

public class RunnableTest2 {
	public static void main(String[] args) {
		Thread t1=new Thread(new RunnableImpl());
		t1.start();
		
		Thread t2=new Thread(new Runnable(){
			public void run(){
				RunnableImpl.show();
			}
		});
		t2.start();
	}
}

class RunnableImpl implements Runnable{
	static void show(){
		for(int i=0;i<10;i++){
			System.out.println(Thread.currentThread().getName()+"--------i---"+i);
		}
	}
	static void show1(){
		for(int i=10;i<20;i++){
			System.out.println(Thread.currentThread().getName()+"--------i---"+i);
		}
	}
	public void run(){
		show();
		//show1();
	}
}


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
*/