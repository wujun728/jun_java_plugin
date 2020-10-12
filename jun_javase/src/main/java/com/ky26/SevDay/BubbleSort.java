package com.ky26.SevDay;

import com.ky26.fiveday.Test;

public class BubbleSort {
	public static void main(String[] args) {
		int[] arr={10,32,67,5,78,43};
		
		/*bubbleSort(arr);
		bubbleSortT(arr);
		bubbleSorts(arr);
		selectSort(arr);
		selectSortT(arr);
		reverse(arr);*/
		selectSorts(arr);
	}
	
	
	static void bubbleSort(int[] array){
		for(int i=0;i<array.length-1;i++){
			for(int j=0;j<array.length-i-1;j++){
				if(array[j]>array[j+1]){
					int temp=array[j];
					array[j]=array[j+1];
					array[j+1]=temp;
				}
			}
		}
		printArrays(array);
	}
	
	static void bubbleSortT(int[] array){
		for(int i=array.length-1;i>=0;i--){
			for(int j=array.length-i-1;j>=1;j--){
				if(array[j]<array[j-1]){
					int temp=array[j];
					array[j]=array[j-1];
					array[j-1]=temp;
				}
			}
		}
		printArrays(array);
	}

	static void bubbleSorts(int[] array){
		for(int i=array.length-1;i>=0;i--){
			for(int j=0;j<i;j++){
				if(array[j]>array[j+1]){
					int temp=array[j];
					array[j]=array[j+1];
					array[j+1]=temp;
				}
			}
		}
		printArrays(array);
	}
	
	static void selectSort(int[] array){
		for(int i=0;i<array.length-1;i++){
			for(int j=i+1;j<array.length;j++){
				if(array[i]>array[j]){
					int temp=array[i];
					array[i]=array[j];
					array[j]=temp;
				}
			}
		}
		printArrays(array);
	}
	
	static void selectSortT(int[] array){
		for(int i=array.length-1;i>=0;i--){
			for(int j=i-1;j>=0;j--){
				if(array[j]>array[i]){
					int temp=array[i];
					array[i]=array[j];
					array[j]=temp;
				}
			}
		}
		printArrays(array);
	}

	static void selectSorts(int[] array){
		for(int i=0;i<array.length-1;i++){
			for(int j=array.length-1;j>i;j--){
				if(array[j]<array[i]){
					int temp=array[i];
					array[i]=array[j];
					array[j]=temp;
				}
			}
		}
		printArrays(array);
	}
	
	static void reverse(int[] array){
		for(int i=0;i<array.length/2;i++){
			int temp=array[i];
			array[i]=array[array.length-1-i];
			array[array.length-1-i]=temp;
		}
		printArrays(array);
	}//反转数组
	
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
	}//输出数组
	
	
}
