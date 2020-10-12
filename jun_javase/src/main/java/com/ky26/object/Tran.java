package com.ky26.object;

public class Tran {
	public static void main(String[] args) throws MyException {
		/*int result=avg(19,190);
		System.out.println(result);*/
		
		try{
			int result=avg(-8,-9);
			System.out.println(result);
		}catch(MyException e){
			System.out.println(e);
		}
	}
	static int avg(int number1,int number2)throws MyException{
		if(number1<0||number2<0){
			throw new MyException("不能使用负数");
		}
		if(number1>100||number2>100){
			throw new MyException("数值太大了");
		}
		return (number1+number2)/2;
	}
}


class MyException extends Exception{
	MyException(String msg){
		super(msg);
	}
}

