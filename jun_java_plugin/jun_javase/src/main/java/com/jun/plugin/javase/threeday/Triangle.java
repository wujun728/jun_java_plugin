package com.jun.plugin.javase.threeday;

public class Triangle {
	public static void main(String[] args) {
		float a=4,b=2,c=9;
		double p=(a+b+c)/2;//���ܳ�
		
		if(a+b<=c||a+c<=b||b+c<=a||a-b>=c||a-c>=b||b-c>=a){
			System.out.println("����������");
		}
		else{
			System.out.println("���Ϊ"+Math.sqrt(p*(p-a)*(p-b)*(p-c)));
		}
	}
}
