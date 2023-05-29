package com.jun.plugin.demo.chap03.sec18;

public class Singleton2 {

	/**
	 * ���췽��˽��
	 */
	private Singleton2(){
		
	}
	
	/**
	 * ����ʽ����ʵ��  �ڵ�һ�ε��õ�ʱ��ʵ����
	 */
	private static Singleton2 single;
	
	/**
	 * ����
	 */
	public synchronized static Singleton2 getInstance(){
		if(single==null){
			// ��һ�ε��õ�ʱ��ʵ����
			System.out.println("��һ�ε��õ�ʱ��ʵ����");
			single=new Singleton2();
		}
		return single;
	}
}
