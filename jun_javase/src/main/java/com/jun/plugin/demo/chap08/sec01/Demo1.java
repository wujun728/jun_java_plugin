package com.jun.plugin.demo.chap08.sec01;

public class Demo1 {

	/**
	 * ������
	 */
	private static void music(){
		for(int i=0;i<1000;i++){
			System.out.println("������");
		}
	}
	
	/**
	 * �Է�
	 */
	private static void eat(){
		for(int i=0;i<1000;i++){
			System.out.println("�Է�");
		}
	}
	
	public static void main(String[] args) {
		/*music();
		eat();*/
		
		/**
		 * ���ö��߳�ʵ��һ�߳Է�һ������
		 */
		Music musicThread=new Music();
		Eat eatThread=new Eat();
		musicThread.start();
		eatThread.start();
	}
}
