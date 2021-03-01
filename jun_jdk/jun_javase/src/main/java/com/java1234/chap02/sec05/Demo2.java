package com.java1234.chap02.sec05;

import java.util.Scanner;

public class Demo2 {

	public static void main(String[] args) {
		System.out.println("请输入一个数字：");
		// 定义一个系统输入对象
		// 自动导包  ctrl+shift+o
		Scanner scanner=new Scanner(System.in);
		int n=scanner.nextInt();
		
		switch(n){
			case 1:{
				System.out.println("用户输入的是1");
				break;
			}
			case 2:{
				System.out.println("用户输入的是2");
				break;
			}
			default:{
				System.out.println("用户输入的是其他数字");
			}
		}
	}
}
