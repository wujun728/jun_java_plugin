package com.ky26.threeday;

public class Prime {
	public static void main(String[] args) {
		
		
		for(int i=2;i<=100;i++){
			int j=2;
			int sum=0;
			while(i%j!=0){
				j++;
			}
			if(j==i){
				sum+=i;
				System.out.print(i+" ");
			}
		}//ÖÊÊý
		
		
		
		
	}
	
/*	static boolean isPrime(int n){
		boolean prime=true;
		for(int i=2;i<=n;i++){
			prime=true;
			for(int j=2;j<i;j++){
				if(i%j==0){
					prime=false;
					break;
				}
			}
			if(prime==true){
				System.out.println(i);
			}
		}
	}*/
}
