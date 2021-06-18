package com.ky26.winterHomeWork.Exercise9;

import java.util.Scanner;

public class StringTest8 {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("请输入数字字符串");
		String str=scan.next();
		parseIntt(str);
	}
	static void parseIntt(String str) {
		int num=0;
		String rege="-?\\+?\\d+";
		if(!str.matches(rege)) {
			System.out.println("你的输入有误");
			return;
		}
		/*for(int i=0;i<str.length();i++) {
			switch(str.substring(i,i+1)) {
			case "1":
				num=num*10+1;
				break;
			case "2":
				num=num*10+2;
				break;
			case "3":
				num=num*10+3;
				break;
			case "4":
				num=num*10+4;
				break;
			case "5":
				num=num*10+5;
				break;
			case "6":
				num=num*10+6;
				break;
			case "7":
				num=num*10+7;
				break;
			case "8":
				num=num*10+8;
				break;
			case "9":
				num=num*10+9;
				break;
			case "0":
				num=num*10+0;
				break;
			}
		}*///JDK1.7及其以上支持switch表达式中为字符串
		if(str.substring(0,1).contains("-")) {
			num=num*(-1);
		}
		System.out.println(num);
	}
}
