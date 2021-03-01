package com.ky26.oneday;

public class Nested {
	public static void main(String[] args) {
		for(int i=1;i<=5;i++){
			for(int j=1;j<=5-i;j++){
				System.out.print("*");
			}
			System.out.println(" ");
		}
		
		for(int i=1;i<=5;i++){
			for(int j=1;j<=i;j++){
				System.out.print("#");
			}
			System.out.println(" ");
		}
		
		for(int i=1;i<=5;i++){
			for(int k=1;k<=i;k++){
				System.out.print(" ");
			}
			for(int j=1;j<=5-i;j++){
				System.out.print("*");
			}
			System.out.println(" ");
		}
		
		for(int i=1;i<=5;i++){
			for(int j=1;j<=5-i;j++){
				System.out.print(" ");
			}//空格递减
			for(int k=1;k<=i;k++){
				System.out.print("*");
			}//星号递增
			System.out.println();
		}
		
		for(int i=1;i<=9;i++){
			for(int j=1;j<=i;j++){
				System.out.print(i+"*"+j+"="+i*j+"\t");
			}
			System.out.println(" ");
		}//乘法表
		
		int sum=0;
		for(int i=0;i<=100;i++){
			if(i%2!=0){
				sum+=i;
				System.out.println("i:"+i);
				System.out.println("sum:"+sum);
			}
		}
		System.out.println("100以内的奇数和为:"+sum);
		
		for(int i=1;i<=9;i++){
			for(int j=1;j<=10-i;j++){
				System.out.print(i+"*"+j+"="+i*j+"\t");
			}
			System.out.println(" ");
		}
		
		/*for(int i=1;i<=5;i++){
			for(int j=0;j<=5-i;j++){
				System.out.print(" ");
			}
			for(int k=1;k<=2*i-1;k++){
				System.out.print("*");
			}
			System.out.println(" ");
		}
		for(int i=1;i<=4;i++){
			for(int k=1;k<=i;k++){
				System.out.print(" ");
			}
			for(int j=5;j>=2*i-1;j++){
				System.out.print("*");
			}
			System.out.println(" ");
		}*/
		

	}

}
