package com.java1234.chap03.sec03;

public class Demo3 {

	/**
	 * ·ÇµÝ¹é
	 * @param n
	 * @return
	 */
	static long notDiGui(int n){
		long result=1;
		for(int i=1;i<=n;i++){
			result=result*i;
		}
		return result;
	}
	
	/**
	 * µÝ¹é
	 * @param n
	 * @return
	 */
	static long diGui(int n){
		if(n==1){
			return 1;
		}
		return diGui(n-1)*n;
	}
	
	public static void main(String[] args) {
		System.out.println("·ÇµÝ¹é£º"+Demo3.notDiGui(5));
		System.out.println("µÝ¹é£º"+Demo3.diGui(5));
	}
}
