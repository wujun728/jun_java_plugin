package com.ky26.threeday;

//import java.util.Scanner;

public class Area {
	public static void main(String[] args) {

		System.out.println("圆的面积是"+area(4));
		System.out.println("长方形的面积是"+area(4,5));
		

		
		
		double x=area(3,4,5);
		if(x!=0){
			System.out.println("三角形的面积是："+x);
		}
		else{
			System.out.println("不是三角形");
		}

		
	}
	
	
	
	static double area(int r){
		return Math.PI*(r*r);
	}
	static int area(int a,int b){
		return a*b;
	}
	static double area(int a,int b,int c){
		double p=(a+b+c)/2;
		
		if(a+b<=c||a+c<=b||b+c<=a||a-b>=c||a-c>=b||b-c>=a){
			return 0;
		}
		else{
			return Math.sqrt(p*(p-a)*(p-b)*(p-c));
		}
	}
	
	
}
