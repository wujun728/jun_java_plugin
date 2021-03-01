package com.ky26.day8;

import java.util.Arrays;
import java.util.Scanner;

public class Exam {
	public static void main(String[] args) {
		/*int[] arrSort={5,2,7,8,12,4,9,7,6};
		for(int i=1;i<arrSort.length;i++){
			for(int j=0;j<arrSort.length-i;j++){
				if(arrSort[j]>arrSort[j+1]){
					int temp=arrSort[j];
					arrSort[j]=arrSort[j+1];
					arrSort[j+1]=temp;
				}
			}
		}
		for(int i=0;i<arrSort.length;i++){
			System.out.print(arrSort[i]+",");
		}
		System.out.println();
		int[] arrSort2={6,3,76,23,16,84,0};
		for(int i=0;i<arrSort2.length;i++){
			for(int j=i+1;j<arrSort2.length;j++){
				if(arrSort2[i]>arrSort2[j]){
					int temp=arrSort2[i];
					arrSort2[i]=arrSort2[j];
					arrSort2[j]=temp;
				}
			}
		}
		for(int i=0;i<arrSort2.length;i++){
			System.out.print(arrSort2[i]+",");
		}
		
		
		System.out.println(fa(11));
		
		
		int num=90;
		int min=2;
		System.out.print(num+"=");
		while(min<=num){
			if(num%min==0){
				if(num==min){
					System.out.print(min);
				}
				else{
					System.out.print(min+"*");
				}
				num=num/min;
			}
			else{
				min++;
			}
		}
		System.out.println();
		
		double sum=0,f=1;
		for(int i=1;i<=100;i++){
			f=i;
			if(f%2==0){
				f=-f;
			}
			sum+=1/f;
		}
		System.out.println(sum);
		
		int[][] arrOld={{1,2},{3,4},{5,6}};
		int n=arrOld.length,m=arrOld[0].length;
		int[][] arrNew=new int[m][n];
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				arrNew[i][j]=arrOld[j][m-i-1];
			}
		}
		for(int i=0;i<arrNew.length;i++){
			for(int j=0;j<arrNew[i].length;j++){
				System.out.print(arrNew[i][j]+",");
			}
			System.out.println();
		}
		
		
		int[] arr={12,5,3,2,8,9,7};
		Arrays.sort(arr);
		int insertNum=11;
		int index=Arrays.binarySearch(arr, insertNum);
		int[] arrInsert=new int[arr.length+1];
		if(index>0){
			index=index+1;
		}
		else{
			index=-index-1;
		}
		for(int i=0;i<arrInsert.length;i++){
			if(i<index){
				arrInsert[i]=arr[i];
			}
			else if(i==index){
				arrInsert[i]=insertNum;
			}
			else{
				arrInsert[i]=arr[i-1];
			}
		}
		
		for(int i=0;i<arrInsert.length;i++){
			System.out.print(arrInsert[i]+",");
		}
		
		
		System.out.println();
		for(int i=1;i<10;i++){
			System.out.print(fa(i)+",");
		}*/
		int[] arrSort2={6,3,76,23,16,84,0};
		for(int i=0;i<arrSort2.length;i++){
			for(int j=i+1;j<arrSort2.length;j++){
				if(arrSort2[i]>arrSort2[j]){
					int temp=arrSort2[i];
					arrSort2[i]=arrSort2[j];
					arrSort2[j]=temp;
				}
			}
		}
		for(int i=0;i<arrSort2.length;i++){
			System.out.print(arrSort2[i]+",");
		}
		
		
		/*int[] arr={12,5,8,9,4,18,1};
		for(int i=1;i<arr.length;i++){
			for(int j=0;j<arr.length-i;j++){
				if(arr[j]>arr[j+1]){
					int temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}
			}
		}
		System.out.println(Arrays.toString(arr));
		
		
		Scanner scan=new Scanner(System.in);
		int num=scan.nextInt();
		int min=2;
		System.out.print(num+"=");
		while(num>=min){
			if(num%min==0){
				if(num==min){
					System.out.print(min);
				}
				else{
					System.out.print(min+"*");
				}
				num=num/min;
			}
			else{
				min++;
			}
		}
		System.out.println();
		
		double sum=0;
		double f=1;
		for(int i=1;i<=100;i++){
			f=i;
			if(f%2==0){
				f=-f;
			}
			sum+=1/f;
		}
		System.out.println(sum);*/


		
		
	}
	
	static int fa(int n){
		if(n>2){
			return fa(n-1)+fa(n-2);
		}
		else{
			return 1;
		}
	}
}
