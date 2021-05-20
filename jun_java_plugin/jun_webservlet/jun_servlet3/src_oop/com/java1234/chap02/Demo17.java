package com.java1234.chap02;

public class Demo17 {

	public static void main(String[] args) {
		outer: //ctrl+shift+/ 多行注释
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(i==1){
					break outer;
				}
				System.out.print("i="+i+" "+"j="+j+"  ");
			}
			System.out.println();
		}
		System.out.println("执行到这里");
	
		/*for(int i=1;i<=10;i++){
			if(i==3 || i==6){
				continue;
			}
			System.out.print("i="+i+" ");
		}*/
		
		/*for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(i==1){
					return;
				}
				System.out.print("i="+i+" "+"j="+j+"  ");
			}
			System.out.println();
		}
		System.out.println("执行到这里");*/
	}
}
