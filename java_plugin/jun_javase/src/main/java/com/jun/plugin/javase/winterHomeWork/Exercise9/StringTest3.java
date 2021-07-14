package com.jun.plugin.javase.winterHomeWork.Exercise9;

import java.util.Scanner;

public class StringTest3 {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("����������");
		String password=scan.next();
		Scanner scana=new Scanner(System.in);
		System.out.println("��ȷ������");
		String passworda=scana.next();
		
		compareString(password,passworda);
	}
	static void compareString(String str1,String str2) {
		if(str1.length()!=str2.length()) {
			System.out.println("���벻һ�£�����������");
		}else {
			boolean flag=true;
			for(int i=0;i<str1.length();i++) {
				char[] arr1=str1.toCharArray();
				char[] arr2=str2.toCharArray();
				if(arr1[i]!=(arr2[i])) {
					flag=false;
					System.out.println("���벻һ�£�����������");
					break;
				}
			}
			System.out.println("ע��ɹ�");
		}
	}
}
