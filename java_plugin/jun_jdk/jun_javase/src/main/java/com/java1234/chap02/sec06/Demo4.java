package com.java1234.chap02.sec06;

public class Demo4 {

	public static void main(String[] args) {
		// 二维数组的静态初始化
		int [][]arr=new int[][]{{1,2,3},{4,5,6},{7,8,9}};
		
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[0].length;j++){
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
		
		// 二维数组的动态初始化
		int [][]arr2=new int[3][3];
		for(int i=0;i<arr2.length;i++){
			for(int j=0;j<arr2[0].length;j++){
				System.out.print(arr2[i][j]+" ");
			}
			System.out.println();
		}
	}
}
