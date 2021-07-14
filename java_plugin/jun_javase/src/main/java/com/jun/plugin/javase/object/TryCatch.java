package com.jun.plugin.javase.object;

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
			System.out.println("����");
		}
		System.out.println("over");
	}
	
	public static int method(int[] arr,int index){
		if(arr==null){
			throw new NullPointerException("����Ϊ��");
		}
		if(index>=arr.length){
			throw new ArrayIndexOutOfBoundsException("�����±�Խ��"+index);
		}
		if(index<0){
			throw new FushuException(index);
		}
		return arr[index];
	}
}

class FushuException extends RuntimeException{
	FushuException(int index){
		super("�±겻��Ϊ����"+index);
	}
}
