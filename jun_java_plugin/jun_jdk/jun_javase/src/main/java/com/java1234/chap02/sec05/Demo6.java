package com.java1234.chap02.sec05;

public class Demo6 {

	public static void main(String[] args) {
		outer:
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(i==1){
					break outer;
				}
				System.out.print("i="+i+" "+"j="+j+" ");
			}
			System.out.println();
		}
	}
}
