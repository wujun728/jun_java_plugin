package com.ky26.twoday;

import java.util.Scanner;

public class HomeWork {
	public static void main(String[] args) {
		
		int m=(int)(Math.random()*100);
//		System.out.println(m);
		while(true){
			Scanner scan=new Scanner(System.in);
			System.out.println("请输入数字：");
			int a=scan.nextInt();
			
			if(a>m){
				System.out.println("输入大了");
			}
			else if(a==m){
				System.out.println("输对了");
				break;
			}
			else{
				System.out.println("输入小了");
			}
		}
		
		
		
		
		
	}
}
