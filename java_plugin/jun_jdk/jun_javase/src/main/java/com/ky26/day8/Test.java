package com.ky26.day8;

import java.util.Arrays;

public class Test {
	public static void main(String[] args) {
		System.out.println("======在排好序的数组中插入数字，保持数组顺序不变");
		int[] arr={1,2,3,4,5,6,7,8,9,12};
		System.out.println(Arrays.toString(arr));
		int key=0;
		int index=Arrays.binarySearch(arr, key);
		int flag=0;
		if(index>=0){
			flag=index;
		}
		else{
			flag=-index-1;
		}
		//System.out.println(index);
		int[] arrNew=new int[arr.length+1];
		for(int i=0;i<=arrNew.length-1;i++){
			if(i<flag){
				arrNew[i]=arr[i];
			}
			else if(i==flag){
				arrNew[i]=key;
			}
			else{
				arrNew[i]=arr[i-1];
			}
		}
		System.out.println(Arrays.toString(arrNew));
		
		
		
		System.out.println("======桃子问题======");
		int sum=0,n=0;
		for(int i=0;i<6;i++){
			sum=n*5+1;
			n=sum;
		}
		System.out.println(sum);
		
		
	}
}
