package com.jun.plugin.demo.chap04.sec03;

public class Demo1 {

	/**
	 * ���쳣��������
	 * @throws NumberFormatException
	 */
	public static void testThrows()throws NumberFormatException{
		String str="123a";
		int a=Integer.parseInt(str);
		System.out.println(a);
	}
	
	public static void main(String[] args) {
		try{
			testThrows();			
			System.out.println("here");
		}catch(Exception e){
			System.out.println("���������ﴦ�����쳣");
			e.printStackTrace();
		}
		System.out.println("I'm here");
	}
	
}
