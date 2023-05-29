package com.jun.plugin.javase.winterHomeWork.Exercise9;

import java.util.Scanner;

public class StringTest5 {
	public static void main(String[] args) {
		int[] arr=new int[7];
		boolean flag=false;
		do {
			for(int i=0;i<7;i++) {
				int num=(int)(Math.random()*30+1);
				arr[i]=num;
			}
			flag=isRepeat(arr);
			if(flag==false) {
				break;
			}
		}while(flag);//������Ʊ�󽱺���
		
		
		Scanner scan=new Scanner(System.in);
		System.out.println("�������߸����֣��ö��Ÿ���");
		String number=scan.next();
		
		String rege="(\\d+,){7}";//ƥ���������
		number=number+",";
		if(!number.matches(rege)) {
			System.out.println("�����������");
			return;
		}
		String[] arr2=number.split(",");
		int[] arr3=new int[arr2.length];
		for(int i=0;i<arr2.length;i++) {
			arr3[i]=Integer.parseInt(arr2[i]);
		}
		int count=repeatCount(arr,arr3);
		if(count==0) {
			System.out.println("��̫��ù��");
		}
		else {
			if(count==7) {
				System.out.println("һ�Ƚ�");
			}else if(count==6) {
				System.out.println("���Ƚ�");
			}else if(count==5) {
				System.out.println("���Ƚ�");
			}else {
				System.out.println("���˽�");
			}
		}
		System.out.println("�󽱺�����");
		for(int i=0;i<arr.length;i++) {
			System.out.print(arr[i]+",");
		}
	}
	static boolean isRepeat(int[] arr) {
		boolean flag=false;
		for(int k=0;k<arr.length;k++) {
			for(int j=k+1;j<arr.length;j++) {
				if(arr[k]==arr[j]) {
					flag=true;//���ظ�
				}
			}
		}
		return flag;
	}
	
	static int repeatCount(int[] arr,int[] arr2) {
		int count=0;
		for(int k=0;k<arr.length;k++) {
			for(int j=0;j<arr2.length;j++) {
				if(arr[k]==arr2[j]) {
					count++;
				}
			}
		}
		return count;
	}
}
