package com.ky26.object.test;

public class Interface {
	public static void main(String[] args) {
		
		
	}
}

interface calculate{
	long fact(int m);
	long intPower(int m,int n);
	boolean findFactor(int m,int n);
}

class Intrtface2 implements calculate {
	
	public long fact(int m){
		if(m<=1){
			return 1;
		}
		else{
			return m*fact(m-1);
		}
	}
	public long intPower(int m,int n){
		int temp=m;
		for(int i=1;i<n;i++){
			m=m*temp;
		}
		return m;
	}
	public boolean findFactor(int m,int n){
		int sum=m+n;
		if(sum>100)
			return true;
		return false;
	}
}
