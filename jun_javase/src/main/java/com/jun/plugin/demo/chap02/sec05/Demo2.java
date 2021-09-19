package com.jun.plugin.demo.chap02.sec05;

import java.util.Scanner;

public class Demo2 {

	public static void main(String[] args) {
		System.out.println("������һ�����֣�");
		// ����һ��ϵͳ�������
		// �Զ�����  ctrl+shift+o
		Scanner scanner=new Scanner(System.in);
		int n=scanner.nextInt();
		
		switch(n){
			case 1:{
				System.out.println("�û��������1");
				break;
			}
			case 2:{
				System.out.println("�û��������2");
				break;
			}
			default:{
				System.out.println("�û����������������");
			}
		}
	}
}
