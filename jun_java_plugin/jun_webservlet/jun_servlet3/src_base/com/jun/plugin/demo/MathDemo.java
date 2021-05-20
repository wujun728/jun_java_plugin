package com.jun.plugin.demo;
//��ʾ��ε���Java API�����ֵ����
public class MathDemo {
	public static void main(String[] args) {
		// ��ʾ����ֵ���������Сֵ������÷�
		System.out.println("abs(-5) = " + Math.abs(-5));
		System.out.println("max(2.72, 3.14) = " + Math.max(2.72, 3.14));
		System.out.println("min(256, 285) = " + Math.min(256, 285));
		// ��ʾ�������뺯����÷�
		System.out.println("round(3.8) = " + Math.round(3.8));
		System.out.println("round(-3.8) = " + Math.round(-3.8));
		// ��ʾ��ƽ��������ݺ�����÷�
		System.out.println("sqrt(2) = " + Math.sqrt(2));
		System.out.println("pow((1+2.25/100), 5) = " + Math.pow((1 + 2.25 / 100), 5));
		// ��ʾָ�����������÷�
		System.out.println("E = " + Math.E);
		System.out.println("exp(2) = " + Math.exp(2));
		System.out.println("log(2) = " + Math.log(2));
		// ��ʾ�컨����ذ庯����÷�
		System.out.println("ceil(3.14) = " + (int) Math.ceil(3.14));
		System.out.println("floor(3.14) = " + (int) Math.floor(3.14));
		// ��ʾ��Ǻ�����÷�
		System.out.println("Pi = " + Math.PI);
		System.out.println("sin(Pi / 2) = " + Math.sin(Math.PI / 2));
		System.out.println("cos(0) = " + Math.cos(0));
	}
}
