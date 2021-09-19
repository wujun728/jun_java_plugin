package com.jun.plugin.javase.sixday;

public class CommonMethod {
	public static void main(String[] args) {
		
		
	}
	
	
	static long factorial(int n){
		if(n==1){
			return 1;
		}
		else{
			return n*factorial(n-1);
		}
	}
	
	static void isPalin(int number){
		int[] array=new int[5];
		for(int i=0;i<array.length;i++){
			array[i]=number%10;
			number=number/10;
		}
		
		if(array[0]==array[array.length-1]&&array[1]==array[array.length-2]){
			System.out.println("�ǻ���");
		}
		else{
			System.out.println("���ǻ���");
		}
	}
	
	static void parseInt(int n){
		int[] a = new int[11];
		int i=0;
		do{
		  a[i] = n%10;
		  n /= 10;
		  i++;
		}while(n!=0);
		System.out.println("��һ��"+i+"λ��");
		System.out.print("ÿλ���ֱ��ǣ�");
		for(int j=i-1;j>=0;j--){
			if(j==0){
				System.out.print(a[j]);
			}
			else{
				System.out.print(a[j]+",");
			}
		}
	}
	
	public static void printArrays(int[] array){
		System.out.print("[");
		for(int i=0;i<array.length;i++){
			if(i==array.length-1){
				System.out.println(array[i]+"]");
			}
			else{
				System.out.print(array[i]+",");
			}
		}
	}//������鷽��
	
	public static void bubbleSort(int[] array){
		for(int i=0;i<array.length-1;i++){
			for(int j=0;j<array.length-i-1;j++){
				if(array[j]>array[j+1]){
					int temp=array[j];
					array[j]=array[j+1];
					array[j+1]=temp;
				}
			}
		}
		//printArrays(array);
	}
	
	static void selectSort(int[] array){
		for(int i=0;i<array.length-1;i++){
			for(int j=i+1;j<array.length;j++){
				if(array[i]>array[j]){
					int temp=array[i];
					array[i]=array[j];
					array[j]=temp;
				}
			}
		}
		printArrays(array);
	}
	
	static void reverse(int[] array){
		for(int i=0;i<array.length/2;i++){
			int temp=array[i];
			array[i]=array[array.length-1-i];
			array[array.length-1-i]=temp;
		}
		printArrays(array);
	}//��������
	
	static void arrayMax(int[] array){
		int max=array[0];
		for(int i=0;i<array.length-1;i++){
			if(array[i]>max){
				max=array[i];
			}
		}
		for(int i=0;i<array.length-1;i++){
			if(array[i]==max){
				System.out.println("���ֵ���±��ǣ�"+i);
			}
		}
		System.out.println("�����е����ֵ�ǣ�"+max);
	}
	
	static int sum(int n){
		if(n>2){
			return sum(n-1)+sum(n-2);
		}
		else{
			return 1;
		}
	}//�ƽ�����

	static void isPrime(int n){
		boolean flag=true;
		for(int i=2;i<n;i++){
			if(n%i==0){
				flag=false;
				System.out.println("��������");
				return;
			}
		}
		if(flag==true){
			System.out.println("������");
		}
	}
	
	public static double arraySum(int[] array){
		double sum=0;
		for(int i=1;i<array.length-1;i++){
			sum=sum+array[i];
		}
		return sum;
	}//��������Ԫ�صĺ�
	
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
	}//�����ά����
	
	

}
