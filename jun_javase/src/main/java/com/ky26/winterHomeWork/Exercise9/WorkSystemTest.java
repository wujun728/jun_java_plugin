package com.ky26.winterHomeWork.Exercise9;

import java.util.Scanner;

public class WorkSystemTest {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("请输入文件名");
		String name=scan.next();
		checkName(name);
		Scanner scant=new Scanner(System.in);
		System.out.println("请输入邮箱");
		String email=scant.next();
		checkemail(email);
	}
	static void checkName(String name) {
		String rege=".+\\.java";
		if(name.matches(rege)) {
			System.out.println("你提交的文件是："+name);
		}else {
			System.out.println("文件名后缀错误,请确认后提交");
		}
	}
	static void checkemail(String email) {
		String rege=".+@[a-zA-Z]+\\.com";
		if(email.matches(rege)) {
			System.out.println("你的邮箱是："+email);
		}else {
			System.out.println("邮箱地址有误");
		}
	}
}
