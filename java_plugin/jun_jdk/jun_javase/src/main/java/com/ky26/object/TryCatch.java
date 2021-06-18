package com.ky26.object;

public class TryCatch {
	public static void main(String[] args) {
		int[] arr=new int[3];
		try{
			System.out.println("--------");
			method(arr,-1);
			return;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("=========");
			//return;
//			System.exit(0);
		}finally{
			System.out.println("结束");
		}
		System.out.println("over");
	}
	
	public static int method(int[] arr,int index){
		if(arr==null){
			throw new NullPointerException("数组为空");
		}
		if(index>=arr.length){
			throw new ArrayIndexOutOfBoundsException("数组下标越界"+index);
		}
		if(index<0){
			throw new FushuException(index);
		}
		return arr[index];
	}
}

class FushuException extends RuntimeException{
	FushuException(int index){
		super("下标不能为负数"+index);
	}
}
