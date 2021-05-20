package com.ky26.twoday;

public class Star {
	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			for(int j=0;j<=i;j++){
				System.out.print("*");
			}
			System.out.println(" ");
		}
		System.out.println("=======================");
		
		for(int i=0;i<=5;i++){
			for(int j=0;j<5-i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<i;k++){
				System.out.print("%");
			}
			System.out.println(" ");
		}
		System.out.println("=======================");
		
		for(int i=0;i<=5;i++){
			for(int j=0;j<=5-i;j++){
				System.out.print("*");
			}
			System.out.println(" ");
		}
		System.out.println("=======================");
		
		for(int i=0;i<=5;i++){
			for(int j=0;j<=i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<=5-i;k++){
				if(k==0){
					System.out.print("@");
				}
				else{
					System.out.print(" ");
				}
				
			}
			System.out.println(" ");
		}
		System.out.println("=======================");

		for(int i=0;i<=4;i++){
			for(int j=0;j<5-i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<i*2+1;k++){
				System.out.print("*");
			}
			System.out.println();
		}
		System.out.println("=======================");
		
		for(int i=0;i<=4;i++){
			for(int j=0;j<5-i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<=4;k++){
				System.out.print("*");
			}
			System.out.println();
		}
		System.out.println("=======================");
		
		for(int i=0;i<=4;i++){
			for(int j=0;j<=4-i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<=i*2;k++){
				System.out.print("*");
			}
			System.out.println();
		}
		for(int i=0;i<=3;i++){
			for(int j=0;j<=i+1;j++){
				System.out.print(" ");
			}
			for(int k=6;k>=i*2;k--){
				System.out.print("*");
			}
			System.out.println(" ");
		}
		
		System.out.println("=======================");
		for(int i=1;i<=4;i++){
			for(int j=1;j<=4-i;j++){
				System.out.print(" ");
			}
			for(int k=1;k<=i*2-1;k++){
				System.out.print("#");
			}
			System.out.println();
		}
		for(int i=1;i<=3;i++){
			for(int j=1;j<=i;j++){
				System.out.print(" ");
			}
			for(int k=5;k>=i*2-1;k--){
				System.out.print("#");
			}
			System.out.println(" ");
		}
		
		System.out.println("=======================");
		
		for(int i=1;i<=5;i++){
			for(int j=0;j<=i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<5-i;k++){
				System.out.print("*");
			}
			System.out.println();
		}
		
		System.out.println("=======================");
		
		for(int i=0;i<=5;i++){
			for(int j=0;j<=i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<11-2*i;k++){
				System.out.print("*");
			}
			System.out.println();
		}
		
		
	}
}
