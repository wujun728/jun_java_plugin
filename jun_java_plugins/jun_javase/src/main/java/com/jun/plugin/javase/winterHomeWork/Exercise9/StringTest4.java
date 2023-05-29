package com.jun.plugin.javase.winterHomeWork.Exercise9;

import java.util.Scanner;

public class StringTest4 {
	public static void main(String[] args) {
		int num=(int)(Math.random()*99+1);
		System.out.println(num);
		while(true) {
			Scanner scan=new Scanner(System.in);
			System.out.println("����������");
			int number=scan.nextInt();
			if(number>num) {
				System.out.println("�´��ˣ����²�");
			}else if(number<num) {
				System.out.println("��С�ˣ����²�");
			}else {
				System.out.println("�¶���");
				break;
			}
		}
	}
}
