package com.java1234.chap02.sec05;

public class Demo3 {

	public static void main(String[] args) {
		// 在控制台输出1到10
		// while 循环语句
		int i=1;
		while(i<11){
			System.out.print(i+" ");
			i++;
		}
		
		System.out.println();
		
		// do....while 循环语句
		int j=1;
		do{
			System.out.print(j+" ");
			j++;
		}while(j<11);
		
		// while  和  do...while的区别
	    // while是先判断后执行    do...while是先执行后判断
		
		System.out.println();
		
		// for 循环
		for(int k=1;k<11;k++){
			System.out.print(k+" ");
		}
		
		System.out.println();
		
		// for循环的嵌套
		for(int m=0;m<10;m++){
			for(int n=0;n<10;n++){
				System.out.print("m="+m+"n="+n+" ");
			}
			System.out.println();
		}
	}
}
