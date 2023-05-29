package com.jun.plugin.javase.winterHomeWork.Exercise9;

import java.util.Scanner;

public class WorkSystemTest {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("�������ļ���");
		String name=scan.next();
		checkName(name);
		Scanner scant=new Scanner(System.in);
		System.out.println("����������");
		String email=scant.next();
		checkemail(email);
	}
	static void checkName(String name) {
		String rege=".+\\.java";
		if(name.matches(rege)) {
			System.out.println("���ύ���ļ��ǣ�"+name);
		}else {
			System.out.println("�ļ�����׺����,��ȷ�Ϻ��ύ");
		}
	}
	static void checkemail(String email) {
		String rege=".+@[a-zA-Z]+\\.com";
		if(email.matches(rege)) {
			System.out.println("��������ǣ�"+email);
		}else {
			System.out.println("�����ַ����");
		}
	}
}
