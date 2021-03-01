package com.ky26.day9;

import java.util.Arrays;

public class Array {
	public static void main(String[] args) {
		/*String[][] arr={{"12345","kdsjf","skfj"},{"16723","ksjfdks"},{"1237986","dsk"},{"12678653","saie"}};
		
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[i].length;j++){
				System.out.print(arr[i][j]+",");
			}
			System.out.println();
		}
		
		String str="你好我好她好";
		char[] ch=str.toCharArray();
		System.out.println(ch.length);
		for(int i=0;i<ch.length;i++){
			System.out.print(ch[i]+",");
		}
		System.out.println();
		String[] str2={"语言","实践","时刻"};
		for(String i:str2){
			System.out.print(i+",");
		}*/
		
		
		int[][] arr={{1,2},{3,4},{5,6}};
		int m=arr[0].length;
		int n=arr.length;
		int[][] arrNew=new int[m][n];
		
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				arrNew[i][j]=arr[n-j-1][i];
				System.out.print(arrNew[i][j]+" ");
			}
			System.out.println();
		}
		
		System.out.println("==============");
		
		/*for(int i=arrNew.length-1;i>=0;i--){
			for(int j=0;j<=arrNew.length;j++){
				System.out.print(arrNew[i][j]+" ");
			}
			System.out.println();
		}*/
		
		
		
	}

}
