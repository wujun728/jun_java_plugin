package com.ky26.day8;

public class Test2 {
	public static void main(String[] args) {
		System.out.println("========冒泡排序==========");
		int[] arr={1,4,4,2,6,8,5,0,7,-1};
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr.length-1;j++){
				if(arr[j]>arr[j+1]){
					int temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}
			}
		}
		printArrays(arr);
		
		System.out.println("========选择排序==========");
		int[] arr2={12,55,39,6,7,65,0,3};
		for(int i=0;i<arr2.length;i++){
			for(int j=i+1;j<arr2.length;j++){
				if(arr2[i]>arr2[j]){
					int temp=arr2[i];
					arr2[i]=arr2[j];
					arr2[j]=temp;
				}
			}
		}
		printArrays(arr2);
		System.out.println("========质数==========");
		for(int i=2;i<100;i++){
			boolean flag=true;
			for(int j=2;j<i;j++){
				if(i%j==0){
					flag=false;
					break;
				}
			}
			if(flag==true){
				System.out.print(i+",");
			}
		}
		System.out.println();
		System.out.println("========兔子生兔子==========");
		System.out.println(fn(11));
		
		System.out.println("========黄金数列==========");
		for(int i=1;i<=10;i++){
			System.out.print(fn(i)+",");
		}//1,1,2,3,5,8,13.....
		
		System.out.println();
		System.out.println("========分解质因数==========");
		int number=90;
		int min=2;
		System.out.print(number+"=");
		while(min<=number){
			if(number%min==0){
				if(min==number){
					System.out.print(min);
				}
				else{
					System.out.print(min+"*");
				}
				number=number/min;
			}
			else{
				min++;
			}
		}
		System.out.println();
		
		System.out.println("========旋转二维数组==========");
		int[][] arrOld={{1,2},{3,4},{5,6}};
		printArr_2(arrOld);
		System.out.println("-------------");
		int n=arrOld.length;
		int m=arrOld[0].length;
		int[][] arrNew=new int[m][n];
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				arrNew[i][j]=arrOld[j][m-i-1];
			}
		}
		printArr_2(arrNew);
		
	}
	
	static void printArr_2(int[][]arr){
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[i].length;j++){
				if(j==arr[i].length-1){
					System.out.print(arr[i][j]);
				}
				else{
					System.out.print(arr[i][j]+",");
				}
			}
			System.out.println();
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
	}
	
	static int fn(int n){
		if(n>2){
			return fn(n-1)+fn(n-2);
		}
		else{
			return 1;
		}
	}
}




