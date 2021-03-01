package com.ky26.object.test2;

public class ThreadTest3 {
	public static void main(String[] args) {
		/*Station s1=new Station();
		Thread t1=new Thread(s1);
		Thread t2=new Thread(s1);
		Thread t3=new Thread(s1);
		t1.start();
		t2.start();
		t3.start();*/
		Bank2 bank=new Bank2();
		Person111 p1=new Person111(bank);
		Person222 p2=new Person222(bank);
		p1.start();
		p2.start();


		
		
	}
}
class Station implements Runnable{
	private int sumTiket=20;
	public void run(){
		while(true){
			synchronized(""){
				if(sumTiket<1){
					System.out.println("票卖完了");
					break;
				}
				else{
					try{
						Thread.sleep(10);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					System.out.println("当前剩余票数："+sumTiket);
					sumTiket--;
				}
			}
		}
	}
}


class Bank2{
	static int sumMoney=1000;
	void counter(int money){
		Bank2.sumMoney-=money;
		System.out.println("柜台取走了"+money);
	}
	void ATM(int money){
		Bank2.sumMoney-=money;
		System.out.println("ATM机器取走了"+money);
	}
}
class Person111 extends Thread{
	Bank2 bank;
	public Person111(Bank2 bank){
		this.bank=bank;
	}
	public void run() {
		while(Bank2.sumMoney>=100){
			bank.counter(100);
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
class Person222 extends Thread{
	Bank2 bank;
	public Person222(Bank2 bank){
		this.bank=bank;
	}
	public void run() {
		while(Bank2.sumMoney>=200){
			bank.ATM(200);
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}


