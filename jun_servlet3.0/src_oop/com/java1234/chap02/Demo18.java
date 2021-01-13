package com.java1234.chap02;

public class Demo18 {

	public static void main(String[] args) {
		/*// 定义一个数组 int类型的数组
		int []arr;
		// 另外一种方式
		int arr2[];*/
		
		/*// 定义一个数组，并且静态初始化
		int arr[]=new int[]{1,2,3};
		
		//普通的数组遍历方式
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
		System.out.println("==============");
		
		// foreach方式
		for(int j:arr){
			System.out.println(j);
		}*/
		
		int arr[]=new int[3];
		arr[0]=1; // 给数组元素赋值
		arr[2]=6; 
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
	}
}
