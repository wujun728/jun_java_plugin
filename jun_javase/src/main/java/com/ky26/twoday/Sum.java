package com.ky26.twoday;

public class Sum {
	public static void main(String[] args) {
		
		double sum = 0;
        for(int i=1; i<=100; i++){
        	sum += 1.0/i;//范围大的与范围小的运算，范围小的自动转为范围大的，double>int
        }
        System.out.println("sum = " + sum);	
        
	}
}
