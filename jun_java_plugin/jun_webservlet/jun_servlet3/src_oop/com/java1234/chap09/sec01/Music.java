package com.java1234.chap09.sec01;

public class Music extends Thread{

	@Override
	public void run() {
		for(int i=0;i<100;i++){
			try {
				Thread.sleep(1000);
				System.out.println("ÌýÒôÀÖ");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
