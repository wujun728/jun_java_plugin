package com.ky26.winterHomeWork.Exercise9;

import java.util.Scanner;

public class StringTest4 {
	public static void main(String[] args) {
		int num=(int)(Math.random()*99+1);
		System.out.println(num);
		while(true) {
			Scanner scan=new Scanner(System.in);
			System.out.println("请输入数字");
			int number=scan.nextInt();
			if(number>num) {
				System.out.println("猜大了，重新猜");
			}else if(number<num) {
				System.out.println("猜小了，重新猜");
			}else {
				System.out.println("猜对了");
				break;
			}
		}
	}
}
