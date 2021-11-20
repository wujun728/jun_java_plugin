package com.jun.plugin.demo.chap02.sec05;

public class Demo3 {

	public static void main(String[] args) {
		// �ڿ���̨���1��10
		// while ѭ�����
		int i=1;
		while(i<11){
			System.out.print(i+" ");
			i++;
		}
		
		System.out.println();
		
		// do....while ѭ�����
		int j=1;
		do{
			System.out.print(j+" ");
			j++;
		}while(j<11);
		
		// while  ��  do...while������
	    // while�����жϺ�ִ��    do...while����ִ�к��ж�
		
		System.out.println();
		
		// for ѭ��
		for(int k=1;k<11;k++){
			System.out.print(k+" ");
		}
		
		System.out.println();
		
		// forѭ����Ƕ��
		for(int m=0;m<10;m++){
			for(int n=0;n<10;n++){
				System.out.print("m="+m+"n="+n+" ");
			}
			System.out.println();
		}
	}
}
