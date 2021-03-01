package com.ky26.SevDay;

import com.ky26.sixday.CommonMethod;

public class HomeWork {
	public static void main(String[] args) {
		int[] arr=new int[5];
		for(int i=0;i<arr.length;i++){
			int a=(int)(Math.random()*9)+0;
			arr[i]=a;
		}
		System.out.print("随机数组为：");
		
		CommonMethod.printArrays(arr);//输出随机数组
		
		boolean have=false;
		for(int i=0;i<arr.length-1;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]==arr[j]){
					have=true;
					break;
					//System.out.println("存在重复");
					//return;
				}
			}
		}
		if(have==true){
			System.out.println("存在重复");
		}
		else{
			System.out.println("不存在重复");
		}
		//System.out.println("不存在重复");

		
		
		int[] arr1={0,1,2,3,4,5,6,7,8,9};
		CommonMethod.bubbleSort(arr1);//排序数组
		arr1[0]=0;
		arr1[arr1.length-1]=0;//赋值最大最小为0
		double sum=CommonMethod.arraySum(arr1);//数组求和
		
		System.out.println("去掉一个最高分和一个最低分，歌手总分是："+sum);
		double avg=sum/(arr1.length-2);
		System.out.println("歌手的最终得分是:"+avg);
		
	}
	
	
}
