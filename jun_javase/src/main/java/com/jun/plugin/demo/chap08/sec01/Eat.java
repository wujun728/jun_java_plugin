package com.jun.plugin.demo.chap08.sec01;

public class Eat extends Thread{

	@Override
	public void run() {
		for(int i=0;i<1000;i++){
			try {
				Thread.sleep(100);
				System.out.println("�Է�");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
