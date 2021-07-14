package com.jun.plugin.demo.chap03.sec03;

public class Demo3 {

	/**
	 * �ǵݹ�
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
	 * �ݹ�
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
		System.out.println("�ǵݹ飺"+Demo3.notDiGui(5));
		System.out.println("�ݹ飺"+Demo3.diGui(5));
	}
}
