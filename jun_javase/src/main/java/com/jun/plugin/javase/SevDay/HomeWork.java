package com.jun.plugin.javase.SevDay;

import com.jun.plugin.javase.sixday.CommonMethod;

public class HomeWork {
	public static void main(String[] args) {
		int[] arr=new int[5];
		for(int i=0;i<arr.length;i++){
			int a=(int)(Math.random()*9)+0;
			arr[i]=a;
		}
		System.out.print("�������Ϊ��");
		
		CommonMethod.printArrays(arr);//����������
		
		boolean have=false;
		for(int i=0;i<arr.length-1;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]==arr[j]){
					have=true;
					break;
					//System.out.println("�����ظ�");
					//return;
				}
			}
		}
		if(have==true){
			System.out.println("�����ظ�");
		}
		else{
			System.out.println("�������ظ�");
		}
		//System.out.println("�������ظ�");

		
		
		int[] arr1={0,1,2,3,4,5,6,7,8,9};
		CommonMethod.bubbleSort(arr1);//��������
		arr1[0]=0;
		arr1[arr1.length-1]=0;//��ֵ�����СΪ0
		double sum=CommonMethod.arraySum(arr1);//�������
		
		System.out.println("ȥ��һ����߷ֺ�һ����ͷ֣������ܷ��ǣ�"+sum);
		double avg=sum/(arr1.length-2);
		System.out.println("���ֵ����յ÷���:"+avg);
		
	}
	
	
}
