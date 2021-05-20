package com.ky26.SevDay;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayAdd {
	public static void main(String[] args) {
		int[] arr={12,5,2,7,4,8,17,47,78};
		Arrays.sort(arr);
		int n=9;
		System.out.println(Arrays.toString(arr));
		int index=Arrays.binarySearch(arr,n);
		System.out.println(index);
		arrayAdd(arr,n,index);
	}
	
	/*static void arrAdd(int[] array,int n,int index){
		int[] arrNew=new int[array.length+1];
		int flag=0;
		if(index>=0){
			flag=index+1;
			System.out.println("插入点flag是"+flag);
		}
		else{
			flag=-index-1;
			System.out.println("原数组不存在该值，插入点flag是"+flag);
		}
		
		for(int i=0;i<=arrNew.length-1;i++){
			if(i<flag){
				arrNew[i]=array[i];
			}
			else if(i==flag){
				arrNew[i]=n;
			}
			else{
				arrNew[i]=array[i-1];
			}
		}
		System.out.println(Arrays.toString(arrNew));
	}
	*/
	static void arrayAdd(int[] array,int n,int index){
		int[] arrayA=new int[array.length+1];
		
		if(index>=0){
			for(int i=0;i<=arrayA.length-1;i++){
				if(i<index){
					arrayA[i]=array[i];
				}
				else if(i==index){
					arrayA[i+1]=n;
					arrayA[i]=array[i];
				}
				else if(i>index){
					arrayA[i]=array[i-1];
				}
			}
			System.out.println(Arrays.toString(arrayA));
		}
		else{
			for(int i=0;i<=arrayA.length-1;i++){
				if(i<-index-1){
					arrayA[i]=array[i];
				}
				else if(i==-index-1){
					arrayA[i]=n;
				}
				else{
					arrayA[i]=array[i-1];
				}
			}
			System.out.println(Arrays.toString(arrayA));
		}
		
	}
	
		
}
