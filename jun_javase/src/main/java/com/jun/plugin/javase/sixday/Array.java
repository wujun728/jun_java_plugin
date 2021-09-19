package com.jun.plugin.javase.sixday;

import java.util.Arrays;

public class Array {
	public static void main(String[] args) {
		int[] arr={2,45,21,6,8,94,100,8,4,123,54,76,90};
		int l=arr.length;
		int[] arr1=Arrays.copyOfRange(arr,0,l);
		
		int temp=0;
		for(int i=0;i<arr.length-1;i++){
			if(arr[i]>arr[i+1]){
				temp=arr[i];
				arr[i]=arr[i+1];
				arr[i+1]=temp;
			}
		}
		
		for(int j=0;j<arr1.length-1;j++){
			if(arr1[j]==temp){
				System.out.println("���ֵ���±��ǣ�"+j);
			}
		}
		System.out.println("����arr�е����ֵ��:"+temp);//�����������ֵ�����±�
		
		
	}
}
