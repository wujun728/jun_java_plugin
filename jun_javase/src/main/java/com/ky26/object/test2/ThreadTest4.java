package com.ky26.object.test2;

public class ThreadTest4 {
public static void main(String[] args) {
		/*TicketImpl ticket=new TicketImpl();
		System.out.println("ticket"+ticket);
		Thread t1=new Thread(ticket);
		Thread t2=new Thread(ticket);
		t1.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ticket.flag=false;
		t2.start();*///ËÀËøÊ¾Àý
		
		Test1 a=new Test1(true);
		Test1 b=new Test1(false);
		Thread t1=new Thread(a);
		Thread t2=new Thread(b);
		t1.start();
		t2.start();
		new String("aaa");
		
		
	}
}

class Test1 implements Runnable{
	private boolean flag;
	Test1(boolean flag){
		this.flag=flag;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(flag){
//			while(true){
				synchronized (MyLock.locka) {
					System.out.println(Thread.currentThread().getName()+"if locka...");
					synchronized (MyLock.lockb) {
						System.out.println(Thread.currentThread().getName()+"if lockb...");
					}
				}
//			}
		}else{
//			while(true){
				synchronized (MyLock.lockb) {
					System.out.println(Thread.currentThread().getName()+"else lockb...");
					synchronized (MyLock.locka) {
						System.out.println(Thread.currentThread().getName()+"else locka...");
					}
				}
//			}
		}
	}
}
class MyLock{
	public static final Object locka=new Object();
	public static final Object lockb=new Object();
}


/*class TicketImpl implements Runnable{
	private static int num=1000;
	Object obj=new Object();
	boolean flag=true;
	public void run(){
		if(flag){
			while(true){
				synchronized(obj){
					show();
				}
			}
		}else{
			while(true){
				show();
			}
		}
	}
	public synchronized void show(){
		synchronized (obj) {
			if(num>0){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"---fun---num="+num--);
			}
		}
	}
}*/
