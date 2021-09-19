package com.jun.plugin.demo.chap04.sec04;

public class Demo1 {

	/**
	 * ����ʱ�쳣������ʱ����飬���Բ�ʹ��try..catch����
	 * @throws RuntimeException
	 */
	public static void testRuntimeException()throws RuntimeException{
		throw new RuntimeException("����ʱ�쳣");
	}
	
	/**
	 * Exception�쳣������ʱ���飬����ʹ��try..catch����
	 * @throws Exception
	 */
	public static void testException()throws Exception{
		throw new Exception("Exception�쳣");
	}
	
	public static void main(String[] args) {
		try{
			testRuntimeException();			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			testException();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
