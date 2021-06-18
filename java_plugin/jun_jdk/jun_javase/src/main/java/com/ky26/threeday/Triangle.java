package com.ky26.threeday;

public class Triangle {
	public static void main(String[] args) {
		float a=4,b=2,c=9;
		double p=(a+b+c)/2;//半周长
		
		if(a+b<=c||a+c<=b||b+c<=a||a-b>=c||a-c>=b||b-c>=a){
			System.out.println("不是三角形");
		}
		else{
			System.out.println("面积为"+Math.sqrt(p*(p-a)*(p-b)*(p-c)));
		}
	}
}
