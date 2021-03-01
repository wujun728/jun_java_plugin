package com.ky26.SevDay;

import com.ky26.sixday.CommonMethod;

public class Index {
	public static void main(String[] args) {
		/*int[] arr={12,4,3,7,89,54,6,1};
		for(int i=0;i<arr.length-1;i++){
			int num=arr[i];
			int index=i;
			for(int j=i+1;j<arr.length;j++){
				if(num>arr[j]){
					num=arr[j];
					index=j;
				}
			}
			if(index!=i){
				int temp=arr[i];
				arr[i]=arr[index];
				arr[index]=temp;
			}
		}
		CommonMethod.printArrays(arr);
		//通过数组下标对数组进行排序*/		
		
		int[] arr={12,21,15,3,4,13,43};
		CommonMethod.bubbleSort(arr);
		CommonMethod.printArrays(arr);
		int n=12;
		int index=0;
		for(int i=0;i<arr.length-1;i++){
			if(n>=arr[i]&&n<arr[i+1]){
				System.out.println("插入数在"+arr[i]+"与"+arr[i+1]+"之间");
				index=i+1;
				break;
			}
			else if(n>=arr[arr.length-1]){
				System.out.println("插入数在数组最大值"+arr[arr.length-1]+"之后");
				index=arr.length;
				break;
			}
			else if(n<arr[0]){
				System.out.println("插入数在数组最小值"+arr[0]+"之前");
				index=0;
				break;
			}
		}
		System.out.println("新插入数字是："+n+","+"插入数组位置："+index);
		
		
		
		/*int[] arr1={5,5,5,5,5,7,7,7};
		//System.out.println(halfSearch(arr1,6));//查找
		System.out.println(halfSearch_2(arr1,4));//插入值下标
*/		
	}
	
	
	
	 static int halfSearch(int[] arr,int key){
		 int min,max,mid;
		 min=0;
		 max=arr.length-1;
		 mid=(min+max)/2;
		 while(key!=arr[mid]){
			 if(key>arr[mid]){
				 min=mid+1;
			 }else if(key<arr[mid]){
				 max=mid-1;
			 }
			 if(min>max){
				 return -1;
			 }
			 mid=(min+max)/2;
			 
		 }
		return mid;
	}//二分查找—第一次出现的下标
	 
	 
	 static int halfSearch_2(int[] arr,int key){
		 int min,max,mid;
		 min=0;
		 max=arr.length-1;
		 while(min<=max){
			 mid=(min+max)/2;
		     if(key>arr[mid]){
			     min=mid+1;
		     }else if(key<arr[mid]){
			     max=mid-1;
		     }else{
			     return mid;
		     }
		 }
		 System.out.println("原数组中无该值");
		 return min-1;
	}//二分法插入数组
	 
	 
}
