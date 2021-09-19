package com.jun.plugin.demo.chap03.sec17;

public class Demo1 {

	public static void main(String[] args) {
		int a=1;
		Integer i=new Integer(a);  // װ�� �ѻ���������ɶ������
		int b=i.intValue(); // ����  �Ѷ��������ɻ�������
		System.out.println("a="+a+",i="+i+",b="+b);
	}
}
