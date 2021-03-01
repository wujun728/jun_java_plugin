package com.ky26.fiveday;

public class Array {
	public static void main(String[] args) {
		
		/*int[] arr=new int[5];
		System.out.print("[");
		for(int i=0;i<arr.length;i++){
			int a=(int)(Math.random()*100)+0;
			arr[i]=a;
			
			if(i==arr.length-1){
				System.out.print(arr[i]);
			}
			else{
				System.out.print(arr[i]+",");
			}
		}
		System.out.print("]");//随机产生给定长度的数组
*/		
		
		
		
		int[] arr1={1,3,5,7,9,6};
		System.out.print("[");
		for(int i=0;i<arr1.length;i++){
			
			int l=arr1.length-i-1;
			
			if(i==arr1.length-1){
				System.out.print(arr1[l]);
			}
			else{
				System.out.print(arr1[l]+",");
			}
		}
		System.out.println("]");//逆序——直接倒叙数组	
		
		
		
		
		/*int[] arr={12,32,231,54,887,5,8,87};
		int count=0;
		for(int i=0;i<arr.length-1;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]>arr[j]){
					int temp=arr[i];
					arr[i]=arr[j];
					arr[j]=temp;
				}
				count++;
			}
		}
		printArrays(arr);
		System.out.println(count);*/
		
		int[] arr2={12,4,65,87,45,23,2,9,56,3};
		for(int i=0;i<arr2.length/2;i++){
			
			int temp=arr2[i];
			arr2[i]=arr2[arr2.length-i-1];
			arr2[arr2.length-i-1]=temp;
			
		}
		printArrays(arr2);//利用中间值——折半逆序数组
		
		
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
}
