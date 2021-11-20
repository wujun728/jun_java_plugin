package com.jun.plugin.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer3 {
	Timer timer;
	int times;
	class RemindTask extends TimerTask {
		public void run() {
			if(times>0){
				System.out.println("Thread Execution!");
				times--;
			}else{
				timer.cancel();//结束timertask
			}
		}
	}
	
	public TestTimer3(int firstTime,int period,int times){
		this.times = times;
		timer = new Timer();
		timer.schedule(new RemindTask(), firstTime*1000, period*1000);
	}
	
	public static void main(String[] args) {
		System.out.println("HelloWorld");
		new TestTimer3(5, 3, 4);
		System.out.println("WorldHello");
	}

}
