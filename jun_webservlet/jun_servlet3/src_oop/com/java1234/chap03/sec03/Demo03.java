package com.java1234.chap03.sec03;

public class Demo03 {

	/**
	 * 非递归方式求阶乘
	 * @param n
	 * @return
	 */
	static long notDigui(int n){
		long result=1;
		for(int i=1;i<=n;i++){
			result=result*i;
		}
		return result;
	}
	
	/**
	 * 递归方式
	 * @param n
	 * @return
	 */
	static long digui(int n){
		if(n==1){
			return 1;
		}
		return n*digui(n-1);
	}
	
	public static void main(String[] args) {
		System.out.println(Demo03.notDigui(5));  // 1*2*3*4*5
		System.out.println(Demo03.digui(5));
	}
	

}
