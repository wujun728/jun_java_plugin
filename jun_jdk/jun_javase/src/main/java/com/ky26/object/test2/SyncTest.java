package com.ky26.object.test2;

public class SyncTest {
	public static void main(String[] args) {
		/*Person1 p1=new Person1();
//		Person1 p2=new Person1();
		Thread t1=new Thread(p1);
		Thread t2=new Thread(p1);
		t1.start();
		t2.start();*/
		
		/*Cus c=new Cus();
		Thread t1=new Thread(c);
		Thread t2=new Thread(c);
		t1.start();
		t2.start();*/
		PersonImpl p1=new PersonImpl();
		Thread t1=new Thread(p1);
		Thread t2=new Thread(p1);
		t1.start();
		t2.start();
	}
}

class Bank111{
	private int sumMoney=0;
	void cunqian(int money){
		sumMoney+=money;
		System.out.println(Thread.currentThread().getName()+"------"+sumMoney);
	}
}

class PersonImpl implements Runnable{
	Bank111 bank=new Bank111();
	public void run(){
		int i=0;
		while(i<13){
			synchronized(""){
				try{
					Thread.sleep(10);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				bank.cunqian(100);
				i++;
			}
		}
	}
}

/*class Bank{
	int sumMoney;
	public void cunqian(int money){
		synchronized(""){
			sumMoney+=money;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("sum="+sumMoney);
		}
	}
}
class Person1 implements Runnable{
	Bank b=new Bank();
	public void run(){
		for(int i=0;i<3;i++){
			b.cunqian(100);
		}
	}
}

class Bank1{
	private int sum;
//	Object obj=new Object();
	public void add(int num){
//		synchronized(obj){
		sum=sum+num;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("sum="+sum);
//		}
	}
}

class Cus implements Runnable{
	private Bank1 b=new Bank1();
	public void run() {
		for(int i=1;i<=3;i++){
			synchronized(""){
				b.add(100);
			}
		}
	}
	
}*/


