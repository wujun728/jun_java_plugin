package book.j2se5.thread;

import java.util.concurrent.Exchanger;

/**
 * Exchanger让两个线程可以互换信息。
 * 例子中服务生线程往空的杯子里倒水，顾客线程从装满水的杯子里喝水，
 * 然后通过Exchanger双方互换杯子，服务生接着往空杯子里倒水，顾客接着喝水，
 * 然后交换，如此周而复始。
 */
public class ExchangerTest {

	// 描述一个装水的杯子
	public static class Cup{
		// 标识杯子是否有水
		private boolean full = false;
		public Cup(boolean full){
			this.full = full;
		}
		// 添水，假设需要5s
		public void addWater(){
			if (!this.full){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				this.full = true;
			}
		}
		// 喝水，假设需要10s
		public void drinkWater(){
			if (this.full){
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}
				this.full = false;
			}
		}
	}
	
	public static void testExchanger() {
		//	初始化一个Exchanger，并规定可交换的信息类型是杯子
		final Exchanger<Cup> exchanger = new Exchanger<Cup>();
		// 初始化一个空的杯子和装满水的杯子
		final Cup initialEmptyCup = new Cup(false); 
		final Cup initialFullCup = new Cup(true);

		//服务生线程
		class Waiter implements Runnable {
			public void run() {
				Cup currentCup = initialEmptyCup;
				try {
					int i=0;
					while (i < 2){
						System.out.println("服务生开始往杯子中添水："
								+ System.currentTimeMillis());
						// 往空的杯子里加水
						currentCup.addWater();
						System.out.println("服务生添水完毕："
								+ System.currentTimeMillis());
						// 杯子满后和顾客的空杯子交换
						System.out.println("服务生等待与顾客交换杯子："
								+ System.currentTimeMillis());
						currentCup = exchanger.exchange(currentCup);
						System.out.println("服务生与顾客交换杯子完毕："
								+ System.currentTimeMillis());
						i++;
					}

				} catch (InterruptedException ex) {
				}
			}
		}

		//顾客线程
		class Customer implements Runnable {
			public void run() {
				Cup currentCup = initialFullCup;
				try {
					int i=0;
					while (i < 2){
						System.out.println("顾客开始喝水："
								+ System.currentTimeMillis());
						//把杯子里的水喝掉
						currentCup.drinkWater();
						System.out.println("顾客喝水完毕："
								+ System.currentTimeMillis());
						//将空杯子和服务生的满杯子交换
						System.out.println("顾客等待与服务生交换杯子："
								+ System.currentTimeMillis());
						currentCup = exchanger.exchange(currentCup);
						System.out.println("顾客与服务生交换杯子完毕："
								+ System.currentTimeMillis());
						i++;
					}
				} catch (InterruptedException ex) {
				}
			}
		}
		
		new Thread(new Waiter()).start();
        new Thread(new Customer()).start();
	}
	
	public static void main(String[] args) {
		ExchangerTest.testExchanger();
	}
}
