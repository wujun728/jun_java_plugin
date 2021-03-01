package com.ky26.object;

import java.util.Arrays;

public class Encapsulate {
	public static void main(String[] args) {
		Person per=new Person();
		per.setAge(12);
		per.setName("Ñ¼µ°");
		per.print();
		
		int[] arr={12,5,8,9,4,18,1};
		for(int i=1;i<arr.length;i++){
			for(int j=0;j<arr.length-i;j++){
				if(arr[j]>arr[j+1]){
					swap(arr,j,j+1);
				}
			}
		}
		System.out.println(Arrays.toString(arr));
	}
	
	
	
	static void swap(int[] arr,int m,int n){
		int temp=arr[m];
		arr[m]=arr[n];
		arr[n]=temp;
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
	}
	
}

class Person{
	private int age;
	private String name;
	
	public void setAge(int age){
		this.age=age;
	}
	public void setName(String name){
		this.name=name;
	}
	public int getAge() {
		return age;
	}
	public String getName() {
		return name;
	}
	
	void print(){
		System.out.println(age+"======"+name);
	}

	
}
