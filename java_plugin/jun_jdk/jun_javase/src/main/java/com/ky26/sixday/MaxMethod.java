package com.ky26.sixday;

public class MaxMethod {
	public static void main(String[] args) {
		/*int[] arr={1,43,23,76,4,8,18};
		int max=arr[0];
		for(int i=0;i<arr.length-1;i++){
			if(arr[i]>max){
				max=arr[i];
			}
		}
		for(int i=0;i<arr.length-1;i++){
			if(arr[i]==max){
				System.out.println("最大值的下标是："+(i+1));
			}
		}
		System.out.println("数组中的最大值是："+max);*/
		
		
		
		/*int[] arr=new int[50];
		int count=1;
		for(int i=0;i<=arr.length-1;i++){
			arr[i]=count;
			count++;

			if(i%3==0&&i!=0){
				arr[i-1]=arr[i];
				//arr[i-1]=0;
			}
		}*/
		/*for(int j=0;j<arr.length;j++){
			System.out.print(arr[j]+",");
		}*///难度较大的题
		
		
		int[] arr1={2,3,2,3,2,3};
		int count=0;
		for(int i=0;i<arr1.length;i++){
			for(int j=arr1.length-1;j>i;j--){
				if(arr1[j]<arr1[i]){
					count++;
				}
			}
		}
		System.out.println(count);//数组的逆序数
		
		
		
		/*int[] arr={1,43,23,76,4,8,18};
		max(arr);//输出数组中的最大值及其下标*/
		
		
	}
	
	public static void max(int[] array){
		int max=array[0];
		for(int i=0;i<array.length-1;i++){
			if(array[i]>max){
				max=array[i];
			}
		}
		for(int i=0;i<array.length-1;i++){
			if(array[i]==max){
				System.out.println("最大值的下标是："+i);
			}
		}
		System.out.println("数组中的最大值是："+max);
	}

}
