package com.ky26.fiveday;

public class Test {
	public static void main(String[] args) {
		System.out.println(sum(2,4));
		System.out.println(height(10));
		
		
	}
	
	
	static int sum(int n,int c){
		int sum=0,d=n;
		for(int i=1;i<=c;i++){
			System.out.print(n+"+");
			sum=sum+n;
			n=n*10+d;
		}
		return sum;
	}
	
	static double height(int count){
		double height=100,sumHeight=100;
		for(int i=1;i<count;i++){
			height=height/2;
			sumHeight=sumHeight+height*2;
		}
		return sumHeight;
	}
	
	
	
}
