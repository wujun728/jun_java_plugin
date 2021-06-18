/*package com.ky26.object.test2;

public class ThreadTest1 {
	public static void main(String[] args) {
		
		Ticket t1=new Ticket();
		Ticket t2=new Ticket();
		Ticket t3=new Ticket();
		Ticket t4=new Ticket();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		TicketImpl ticket=new TicketImpl();
		Thread t11=new Thread(ticket);
		Thread t22=new Thread(ticket);
		t11.start();
		t22.start();
	}
}
class Ticket extends Thread{
	private int num=100;
	public void run(){
		while(true){
			if(num>0){
				System.out.println(Thread.currentThread().getName()+"---sale---"+num--);
			}
		}
	}
}//两种方式实现多线程
class TicketImpl implements Runnable{
	private int num=100;
	boolean flag=true;
	public void run(){
		while(true){
			if(flag){
				synchronized(""){
					if(num>0){
						try{
							Thread.sleep(10);
						}catch(Exception e){
								e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+"---object---"+num--);
					}
				}
			}
			else{
				show();
			}
		}
	}
	public synchronized void  show(){
		if(num>0){
			try{
				Thread.sleep(10);
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"---function---"+num--);
		}
	}
}
*/