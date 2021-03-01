package com.ky26.SevDay;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.text.DateFormatter;

import com.ky26.sixday.CommonMethod;

public class StringO {
	public static void main(String[] args) {
		String str="www.baidu.com.www.3g.com.cn";
		String[] str2=str.split("\\.");
		for(String a:str2){
			System.out.print("["+a+"]");
		}
		System.out.println();
		System.out.println(str.contains(".b"));
		
//		Date date=new Date();
//		String year=String.format("%tY",date);
//		String mouth=String.format("%tB",date);
//		String day=String.format("%td",date);
//		System.out.print(year+",");
//		System.out.print(mouth+",");
//		System.out.print(day);
//		System.out.println();本地时间格式化
		
		int[] str3={2,4,1,6,5,9};
		Arrays.fill(str3,1,3,0);//[fromIndex,toIndex);
		System.out.println(Arrays.toString(str3));
		
		String idNumber="500102199510152168";
		String arayBurn=idNumber.substring(0,6);
		String burnDate=idNumber.substring(6,14);
		System.out.println("出生日期"+burnDate);
		
		//SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM-dd");
		
		//Date date=new Date();
		//String s=
		
		
		
		
		
		
		/*int[] str4={12,3,6,8,43};
		int[] str5={4,12,6,8,13};
		int[] str6=new int[str4.length+str5.length];
		System.arraycopy(str4, 0, str6, 0, str4.length);
		System.arraycopy(str5, 0, str6, str4.length, str5.length);
		Arrays.sort(str6);
		System.out.println(Arrays.toString(str6));*/
		/*int index=Arrays.binarySearch(str6,6);
		int index1=Arrays.binarySearch(str6,12);
		System.out.println(index);
		System.out.println(index1);*/
		
		System.out.println("==========================");
		
		/*int a=12,b=9,c=11;
		System.out.println(Math.abs(a-b));
		System.out.println(Math.abs(b-c));
		System.out.println(Math.abs(a-c));*/
		
		/*int[] str7={7,4,9,23,41,2,8,54,120,89,19};
		int[] str8=new int[str7.length+1];
		int n=40;
		Arrays.sort(str7);
		int index=halfSearch_2(str7,n);
		System.out.println(index);
		System.arraycopy(str7, 0, str8, 0, index);
		str8[index]=n;
		System.arraycopy(str7, index, str8, index+1, str7.length-index);
		System.out.println("原始数组排序后:"+Arrays.toString(str7));
		System.out.println("插入数字后:"+Arrays.toString(str8));*/
		
		/*int[] strY={2,3,67,8,12,32,9,7};
		Arrays.sort(strY);
		int n=9;
		int index=Arrays.binarySearch(strY,n);
		int[] strN=new int[strY.length+1];
		if(index<0){
			index=-index-1;
		}
		System.out.println("新插入值下标："+index);
		System.out.println("原始数组排序后:"+Arrays.toString(strY));
		System.arraycopy(strY, 0, strN, 0, index);
		strN[index]=n;
		System.arraycopy(strY, index, strN, index+1, strY.length-index);
		System.out.println("新数组:"+Arrays.toString(strN));*/
		
		/*int[] arr={2,9,15,28,30};
		int[] arr1=new int[arr.length+1];
		int m=30;
		for(int i=0;i<arr.length;i++){
			arr1[i]=arr[i];
		}
		System.out.println(Arrays.toString(arr1));
		System.out.println("-------------");
		for(int i=0;i<arr1.length-1;i++){
			if(m>arr1[arr1.length-1-1]){
				arr1[arr1.length-1]=m;
			}
			if((arr1[i]<m)&&(arr1[i+1]>=m)){
				for(int j=i;j<arr1.length-1;j++){
					arr1[j+1]=arr[j];
				}
				arr1[i+1]=m;
				break;
			}
			if(m<=arr1[0]){
				for(int j=0;j<arr1.length-1;j++){
					arr1[j+1]=arr[j];
				}
				arr1[0]=m;
				break;
				
				arr1[0]=m;
				arr1[i+1]=arr[i];
			}
		}
		System.out.println(Arrays.toString(arr1));//数组中插入新数字
*/		
		
		
		/*int[] arr={12,5,2,7,4,8,17,47,78};
		Arrays.sort(arr);
		System.out.println(Arrays.toString(arr));
		int n=89;
		int index=Arrays.binarySearch(arr,n);
		System.out.println(index);
		insert(arr,n,index);*/
		
		
		/*int[] arr2={12,4,32,1,6,9,45};
		Arrays.sort(arr2);
		System.out.println(Arrays.toString(arr2));
		int index2=Arrays.binarySearch(arr2,400);
		System.out.println(index2);*/
		
		
	}
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
		 return min;
	}

	
	static void insert(int[] arr,int n,int index){
		int[] arr2=new int[arr.length+1];
		if(index>=0){
			for(int i=0;i<arr2.length-1;i++){
				if(i<index){
					arr2[i]=arr[i];
				}
				else if(index==i){
					arr2[i]=arr[i];
					arr2[i+1]=n;
				}
				else{
					arr2[i+1]=arr[i];
				}
			}
		}
		else{
			for(int i=0;i<arr2.length;i++){
				if(i<-index-1){
					arr2[i]=arr[i];
				}
				else if(i==-index-1){
					//arr2[i]=arr[i];
					arr2[i]=n;
				}
				else{
					arr2[i]=arr[i-1];
				}
			}
		}
		System.out.println(Arrays.toString(arr2));
	}


}
