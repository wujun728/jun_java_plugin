package com.ky26.threeday;

public class HomeWork {
	public static void main(String[] args) {
		for(int i=0;i<=5;i++){
			for(int j=0;j<5-i;j++){
				System.out.print("*");
			}
			System.out.println();
		}
		
		for(int i=1;i<=5;i++){
			for(int j=0;j<=5-i;j++){
				System.out.print(6-i);
			}
			System.out.println();
		}
	}
}
