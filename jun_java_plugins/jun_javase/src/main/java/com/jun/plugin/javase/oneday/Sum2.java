package com.jun.plugin.javase.oneday;

import java.util.Scanner;

public class Sum2 {
	public static void main(String[] args) {
		
		/*Scanner scan=new Scanner(System.in);
		System.out.println("���������֣�");
		int week=scan.nextInt();*/
		int week=5;
		
		/*if(week==1){
			System.out.println("����һ");
		}
		else if(week==2){
			System.out.println("���ڶ�");
		}
		else if(week==3){
			System.out.println("������");
		}
		else if(week==4){
			System.out.println("������");
		}
		else if(week==5){
			System.out.println("������");
		}
		else if(week==6){
			System.out.println("������");
		}
		else {
			System.out.println("������");
		}*/
		
		switch (week){
			case 1:
				System.out.println("����1");
				break;
			case 2:
				System.out.println("����2");
				break;
			case 3:
				System.out.println("����3");
				break;
			case 4:
				System.out.println("����4");
				break;
			case 5:
				System.out.println("����5");
				break;
			case 6:
				System.out.println("����6");
				break;
			case 7:
				System.out.println("������");
		}
		
		int x=1;
		int sum=0;
		while(x<=100){
			sum+=x;
			x++;
		}
		System.out.println(sum);
	}
}
