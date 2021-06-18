package com.ky26.winterHomeWork.Exercise9;

import java.util.Scanner;

public class StringTest2 {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("请输入字母串");
		String name=scan.next();
		checkString(name);
	}
	static void checkString(String name) {
		System.out.println("你输入的字母串是："+name);
		String rege="[a-zA-Z]+";
		String reget="\\p{Upper}+[a-zA-Z]*";
		if(!name.matches(rege)) {
			System.out.println("输入有误，请输入字母串！");
		}
		else if(!name.matches(reget)){
			System.out.println("第一个字母不是大写字母");
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
			System.out.println("字符串中一共有"+count+"个大写字母");
		}
	}
}
