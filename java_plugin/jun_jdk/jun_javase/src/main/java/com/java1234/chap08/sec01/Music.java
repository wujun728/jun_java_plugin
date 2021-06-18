package com.java1234.chap08.sec01;

public class Music extends Thread{

	@Override
	public void run() {
		for(int i=0;i<1000;i++){
			try {
				Thread.sleep(100);
				System.out.println("ÌýÒôÀÖ");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
