package com.java1234.chap04.sec02;

public class Demo2 {

	public static void testFinally(){
		String str="123a";
		try{
			int a=Integer.parseInt(str);	
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("exception");
			return;
		}finally{
			System.out.println("finally end");
		}
		System.out.println("end");
	}
	
	public static void main(String[] args) {
		testFinally();
	}
}
