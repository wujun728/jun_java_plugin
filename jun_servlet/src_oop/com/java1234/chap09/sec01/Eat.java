package com.java1234.chap09.sec01;

public class Eat extends Thread{

	@Override
	public void run() {
		for(int i=0;i<100;i++){
			try {
				Thread.sleep(1000);
				System.out.println("³Ô·¹");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
