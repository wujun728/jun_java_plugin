package com.jun.plugin.javase.winterHomeWork.Exercise9;

import java.util.Scanner;

public class StringTest2 {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("��������ĸ��");
		String name=scan.next();
		checkString(name);
	}
	static void checkString(String name) {
		System.out.println("���������ĸ���ǣ�"+name);
		String rege="[a-zA-Z]+";
		String reget="\\p{Upper}+[a-zA-Z]*";
		if(!name.matches(rege)) {
			System.out.println("����������������ĸ����");
		}
		else if(!name.matches(reget)){
			System.out.println("��һ����ĸ���Ǵ�д��ĸ");
		}
		else if(name.matches(rege)&&name.matches(reget)) {
			char[] arr=name.toCharArray();
			int count=0;
			for(int i=0;i<arr.length;i++) {
				String temp=String.valueOf(arr[i]);
				if(temp.matches(reget)) {
					count++;
				}
			}
			System.out.println("�ַ�����һ����"+count+"����д��ĸ");
		}
	}
}
