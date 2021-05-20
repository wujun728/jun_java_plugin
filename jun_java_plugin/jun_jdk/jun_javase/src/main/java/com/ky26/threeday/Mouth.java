package com.ky26.threeday;

public class Mouth {
	public static void main(String[] args) {
		int mouth=4;
		
		
		if(mouth==3||mouth==4||mouth==5){
			System.out.println("春季");
		}
		else if(mouth==6||mouth==7||mouth==8){
			System.out.println("夏季");
		}
		else if(mouth==9||mouth==10||mouth==11){
			System.out.println("秋季");
		}
		else if(mouth==12||mouth==1||mouth==2){
			System.out.println("冬季");
		}
		else{
			System.out.println("请输入1-12之间的月份");
		}
	}
}
