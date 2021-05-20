package com.java1234.chap04.sec03;

public class Demo4 {

	/**
	 * 向方法的调用者抛出异常
	 * @param a
	 * @throws Exception
	 */
	public static void testThrow(int a)throws Exception{
		if(a==1){
			// 直接抛出一个异常类
			throw new Exception("有异常");
		}
		System.out.println(a);
	}
	
	public static void main(String[] args) {
		try {
			testThrow(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
