package com.java1234.chap02;

public class Demo20 {

	public static void main(String[] args) {
		int arr[]={4,21,0,-12,-3};
		int temp;
		// 循环次数 n-1次
		for(int i=0;i<arr.length-1;i++){
			// 比较次数 n-1-i
			for(int j=0;j<arr.length-1-i;j++){
				// 假如前面的一个数大于后面一个数，则交换数据
				if(arr[j]>arr[j+1]){
					temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}
			}
		}
		
		for(int a:arr){
			System.out.print(a+" ");
		}
	}
}
