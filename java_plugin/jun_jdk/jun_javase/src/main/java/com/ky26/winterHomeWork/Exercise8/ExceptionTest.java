package com.ky26.winterHomeWork.Exercise8;

public class ExceptionTest {
	public static void main(String[] args) throws MyException {
		threwException();
	}
	static void threwException() throws MyException{
		int num=(int)(Math.random()*100);
		if(num>50) {
			System.out.println(num);
			throw new MyException("数值大于50");
		}
		else {
			System.out.println(num);
			System.out.println("数字小于50，不抛出异常");
		}
	}
}
class MyException extends Exception{
	public MyException(String ExceptionMes){
		super(ExceptionMes);
	}
}
