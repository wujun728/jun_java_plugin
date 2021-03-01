package com.ky26.oneday;

import java.util.Scanner;

public class Sum2 {
	public static void main(String[] args) {
		
		/*Scanner scan=new Scanner(System.in);
		System.out.println("请输入数字：");
		int week=scan.nextInt();*/
		int week=5;
		
		/*if(week==1){
			System.out.println("星期一");
		}
		else if(week==2){
			System.out.println("星期二");
		}
		else if(week==3){
			System.out.println("星期三");
		}
		else if(week==4){
			System.out.println("星期四");
		}
		else if(week==5){
			System.out.println("星期五");
		}
		else if(week==6){
			System.out.println("星期六");
		}
		else {
			System.out.println("星期日");
		}*/
		
		switch (week){
			case 1:
				System.out.println("星期1");
				break;
			case 2:
				System.out.println("星期2");
				break;
			case 3:
				System.out.println("星期3");
				break;
			case 4:
				System.out.println("星期4");
				break;
			case 5:
				System.out.println("星期5");
				break;
			case 6:
				System.out.println("星期6");
				break;
			case 7:
				System.out.println("星期日");
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
