package com.ky26.winterHomeWork;

import java.util.Scanner;

public class ArrayTest {
	public static void main(String[] args) {
//		int[] arr= {1,2,3,4,5};
//		printArray(arr);
		
		Scanner scan=new Scanner(System.in);
		System.out.println("请输入数组项数：");
		int n=scan.nextInt();
		printArray(myArray(n));
	}
	static void printArray(int[] array) {
		System.out.print("[");
		for(int i=0;i<array.length;i++) {
			if(i==array.length-1) {
				System.out.print(array[i]);
				System.out.println("]");
			}
			else {
				System.out.print(array[i]+",");
			}
		}
	}
	static int[] myArray(int n) {
		int[] arr=new int[n];
		for(int i=0;i<n;i++) {
			int a=(int)(Math.random()*50+50);
			arr[i]=a;
		}
		return arr;
	}
}
