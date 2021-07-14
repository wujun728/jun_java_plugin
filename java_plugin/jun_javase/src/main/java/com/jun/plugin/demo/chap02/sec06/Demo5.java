package com.jun.plugin.demo.chap02.sec06;

public class Demo5 {

	public static void main(String[] args) {
		int temp;
		int []arr={4,21,0,-12,-3};
		
		// ѭ���Ĵ��� n-1��
		for(int i=0;i<arr.length-1;i++){
			// �Ƚϴ��� n-1-i
			for(int j=0;j<arr.length-1-i;j++){
				// ����ǰ��һ�������ں���һ�������򽻻�����
				if(arr[j]>arr[j+1]){
					temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}
			}
		}
		
		for(int i:arr){
			System.out.print(i+" ");
		}
	}
}
