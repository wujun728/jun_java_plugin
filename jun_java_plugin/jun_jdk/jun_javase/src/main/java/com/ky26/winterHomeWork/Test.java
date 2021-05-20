package com.ky26.winterHomeWork;

public class Test {
	public static void main(String[] args) {
		int[] arr={1,2,3,4,5,6,7,8,9};
		int number=(int)(Math.random()*8+1);
		boolean flag=false;
		for(int i=0;i<arr.length;i++){
			if(number==arr[i]){
				flag=true;
				break;
			}
		}
		
		System.out.println(number);
	}
}
