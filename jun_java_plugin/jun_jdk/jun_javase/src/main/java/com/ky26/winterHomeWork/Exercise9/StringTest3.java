package com.ky26.winterHomeWork.Exercise9;

import java.util.Scanner;

public class StringTest3 {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("请输入密码");
		String password=scan.next();
		Scanner scana=new Scanner(System.in);
		System.out.println("请确认密码");
		String passworda=scana.next();
		
		compareString(password,passworda);
	}
	static void compareString(String str1,String str2) {
		if(str1.length()!=str2.length()) {
			System.out.println("密码不一致，请重新输入");
		}else {
			boolean flag=true;
			for(int i=0;i<str1.length();i++) {
				char[] arr1=str1.toCharArray();
				char[] arr2=str2.toCharArray();
				if(arr1[i]!=(arr2[i])) {
					flag=false;
					System.out.println("密码不一致，请重新输入");
					break;
				}
			}
			System.out.println("注册成功");
		}
	}
}
