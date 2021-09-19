package com.jun.plugin.javase.day8;
import java.util.Scanner;
public class StringT {
	public static void main(String[] args) {
		System.out.println("======�ؼ�������======");
		
		String[] arr={"����������3-5�ȣ��磬����","�Ͼ�������-3-5�ȣ��꣬�Ϸ�","���죬����5-10�ȣ��磬������","�ɶ�������3-8�ȣ�С�꣬���Ϸ�","����������3-5�ȣ�ѩ��ƫ����","�Ϻ�������5-9�ȣ��磬ƫ�Ϸ�"};
		
		Scanner scan=new Scanner(System.in);
		System.out.println("������ؼ���������");
		String key=scan.next();
		boolean find=true;
		for(int i=0;i<arr.length;i++){
			if(arr[i].contains(key)){
				System.out.println(arr[i]);
				find=false;
			}
			else{
				continue;
			}
			
		}
		
		if(find==true){
			System.out.println("�޽��");
		}

		
		
	}
	
	
}
