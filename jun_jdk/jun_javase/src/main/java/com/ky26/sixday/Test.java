package com.ky26.sixday;

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		/*int[] array={63,4,24,1,3,15};

		Test sorter=new Test();
		sorter.sort(array);//调用方法——冒泡排序
*/		
		/*for(int i=0,j=array.length-1;i<j;i++,j--){
			int temp=array[i];
			array[i]=array[j];
			array[j]=temp;
		}*///交换位置逆序输出——老师的
		
		/*int[] array={63,4,24,1,3,15};
		for(int i=0;i<array.length-1;i++){
			int temp=array[i];
			array[i]=array[array.length-i-1];
			array[array.length-i-1]=temp;
		}//交换位置逆序输出——有错
		
		for(int j=0;j<array.length;j++){
			System.out.print(array[j]+",");
		}*///输出数组的每一项
		
		/*double m=1,n=2,sum=0;
		for(int i=0;i<20;i++){
			double j=m;
			double k=n;
			sum=sum+(n/m);
			n=k+j;
			m=k;
		}
		System.out.println(sum);//求和2/1，3/2，5/3，8/5，13/8，21/13...*/		
		
		
		/*long sum=0;
		for(int i=1;i<=20;i++){
			sum+=factorial(i);
		}
		System.out.println(sum);//求和1+2!+3!+...+20!*/		
		
		
		/*int a=12321;
		isPalin(a);//判断回文*/		
		
		int n=1030121340;
		parseInt(n);//判断整数位数及其每位数的数值		
		

		
		
		
		
		
		
		/*System.out.print("请输入一个整数：");
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		scan.close();
		//定义数组变量标识某人是否还在圈内
		boolean[] isIn = new boolean[n];
		for(int i=0;i<isIn.length;i++)
			 isIn[i] = true;
		//定义圈内人数、报数、索引
		int inCount = n;
		int countNum = 0;
		int index = 0;
		while(inCount>1){
			if(isIn[index]){
				countNum++;
				if(countNum==3){
					countNum = 0;
					isIn[index] = false;
					inCount--;
				}
			}
			index++;
			if(index==n)
				 index = 0;
		}
		for(int i=0;i<n;i++)
			 if(isIn[i])
			   System.out.println("留下的是："+(i+1));*/


		
	}
	
	
	public void sort(int[] array){
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array.length-1;j++){
				if(array[j]>array[j+1]){
					int temp=array[j];
					array[j]=array[j+1];
					array[j+1]=temp;
				}
				
			}
		}
		printArrays(array);
	}
	
/*	public void showArray(int[] array){
		for(int i:array){
			System.out.print("<"+i);
		}
		System.out.println();
	}*/
	
	static long factorial(int n){
		if(n==1){
			return 1;
		}
		else{
			return n*factorial(n-1);
		}
	}
	
	static void isPalin(int number){
		int[] array=new int[5];
		for(int i=0;i<array.length;i++){
			array[i]=number%10;
			number=number/10;
		}
		
		if(array[0]==array[array.length-1]&&array[1]==array[array.length-2]){
			System.out.println("是回文");
		}
		else{
			System.out.println("不是回文");
		}
	}
	
	static void parseInt(int n){
		int[] a = new int[11];
		int i=0;
		do{
		  a[i] = n%10;
		  n /= 10;
		  i++;
		}while(n!=0);
		System.out.println("是一个"+i+"位数");
		System.out.print("每位数分别是：");
		for(int j=i-1;j>=0;j--){
			if(j==0){
				System.out.print(a[j]);
			}
			else{
				System.out.print(a[j]+",");
			}
		}
	}
	
	static void printArrays(int[] array){
		System.out.print("[");
		for(int i=0;i<array.length;i++){
			if(i==array.length-1){
				System.out.println(array[i]+"]");
			}
			else{
				System.out.print(array[i]+",");
			}
		}
	}//输出数组方法
	
	
	
}
