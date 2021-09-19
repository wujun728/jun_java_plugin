package com.jun.plugin.demo.chap04.sec05;

public class TestCustomException {

	public static void test()throws CustomException{
		throw new CustomException("�Զ����쳣");
	}
	
	public static void main(String[] args) {
		try {
			test();
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
