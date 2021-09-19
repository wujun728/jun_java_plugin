package com.jun.plugin.javase.winterHomeWork.Exercise9;

import java.util.Scanner;

public class StringTest7 {
	public static void main(String[] args) {
		toBinaryStringg(-10);//�Զ��巽����������
		
		/*Scanner scan=new Scanner(System.in);
		System.out.println("����������");
		int number=scan.nextInt();
		String str=Integer.toBinaryString(number);
		System.out.println(str);//���÷���
*/	}
	static void toBinaryStringg(int number) {
		String s="";
		while(number!=0||number!=1) {
			if(number%2==0) {
				s=s+0;
			}else {
				s=s+1;
			}
			number/=2;
			if(number==0) {
				break;
			}else if(number==1) {
				s=s+1;
				break;
			}
		}
		String ss="";
		for(int i=s.length()-1;i>=0;i--) {
			ss+=s.substring(i, i+1);
		}
		System.out.println(ss);
	}
}
