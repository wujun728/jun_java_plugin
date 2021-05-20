package com.ky26.twoday;

public class Operation {
	public static void main(String[] args) {
//		int  a=5,b=3;
//		float a=5,b=3;
//		double a=5,b=3;
		int a=5;
		double b=3;
		char opr='/';
		
		switch(opr){
			case '+':System.out.println(a+b);
			break;
			case '-':System.out.println(a-b);
			break;
			case '*':System.out.println(a*b);
			break;
			case '/':System.out.println(a/b);
			break;
			case '%':System.out.println(a%b);
			break;
		}
		
	}
}
