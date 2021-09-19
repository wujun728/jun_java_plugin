package com.jun.plugin.javase.twoday;

import java.util.Scanner;

public class HomeWork {
	public static void main(String[] args) {
		
		int m=(int)(Math.random()*100);
//		System.out.println(m);
		while(true){
			Scanner scan=new Scanner(System.in);
			System.out.println("���������֣�");
			int a=scan.nextInt();
			
			if(a>m){
				System.out.println("�������");
			}
			else if(a==m){
				System.out.println("�����");
				break;
			}
			else{
				System.out.println("����С��");
			}
		}
		
		
		
		
		
	}
}
