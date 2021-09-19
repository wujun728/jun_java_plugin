package com.jun.plugin.demo.chap02.sec06;

public class Demo2 {

	public static void main(String[] args) {
		// ����һ�����飬���Ҿ�̬��ʼ��
		int arr[]=new int[]{1,2,3};
		
		// ��ͨ�ı������鷽ʽ
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
		
		System.out.println("--------");
		
		// foreach������������
		for(int j:arr){
			System.out.println(j);
		}
	}
}
