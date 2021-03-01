package com.ky26.day9;

public class ArrayT {
	public static void main(String[] args) {
		/*int[][] arr={{1,2},{3,4},{5,6}};
		int n=arr.length;
		int m=arr[0].length;
		//System.out.println(m);
		int[][] arrNew=new int[m][n];
		
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				arrNew[i][j]=arr[j][m-i-1];
				System.out.print(arrNew[i][j]+" ");
			}
			System.out.println();
		}*/
		
		System.out.println("È¡Óà"+(2%4));
		
		
		int[][] arr2={{1,2},{3,4},{5,6}};
		printArr_2(arr2);
		System.out.println("===========");
		int deg=3;
		printArr_2(rotateArr(arr2,deg));

	}
	
	
 	static int[][] rotateArr(int[][] arr){
		int m=arr[0].length;
		int n=arr.length;
		int[][] arrNew=new int[m][n];
		
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				arrNew[i][j]=arr[j][m-i-1];
			}
		}
		return arrNew;
	}
	
 	
 	static int[][] rotateArr(int[][] arr,int deg){
 		if(deg%4==1){
 			return rotateArr(arr);
 		}
 		else{
 			deg=deg%4+4;
 			return  rotateArr(rotateArr(arr,deg-1));
 		}
 	}
	
	

	/*static void rotateArr(int[][] arr){
		int m=arr[0].length;
		int n=arr.length;
		int[][] arrNew=new int[m][n];
		
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				arrNew[i][j]=arr[j][m-i-1];
				System.out.print(arrNew[i][j]+" ");
			}
			System.out.println();
		}
	}
	//ÄæÊ±Õë90
	static void rotateArr2(int[][] arr){
		int n=arr[0].length;
		int m=arr.length;
		int[][] arrNew=new int[m][n];
		
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				arrNew[i][j]=arr[2-i][1-j];
				System.out.print(arrNew[i][j]+" ");
			}
			System.out.println();
		}
	}
	//ÄæÊ±Õë180
	static void rotateArr3(int[][] arr){
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
	}//ÄæÊ±Õë270*/
	
	

	static void printArr_2(int[][]arr){
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[i].length;j++){
				if(j==arr[i].length-1){
					System.out.print(arr[i][j]);
				}
				else{
					System.out.print(arr[i][j]+",");
				}
			}
			System.out.println();
		}
	}
}
