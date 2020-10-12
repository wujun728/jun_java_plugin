package com.ky26.object;

public class Exception1 {
	public static void main(String[] args) {
		int[] arr1=new int[4];
		Exception1 e=new Exception1();
		System.out.println(e.method(arr1, -3));
	}
	
	public int method(int[] arr,int index){
		if(arr==null){
			throw new NullPointerException("数组为空");
		}
		if(index>=arr.length){
			throw new ArrayIndexOutOfBoundsException("数组下标越界"+index);
		}
		if(index<0){
			throw new FushuException1(index);
		}
		return arr[index];
	}
}



class FushuException1 extends RuntimeException{
	FushuException1(int index){
		super("下标不能为负数"+index);
	}
}
