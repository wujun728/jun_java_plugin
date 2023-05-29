package com.jun.plugin.javase.twoday;
import java.util.Scanner;

public class Practice {
	public static void main(String[] args) {
		
		
		/*char c1=0+'0';
		char c2=0+'A';
		char c3=0+'a';
		System.out.println(c1+" "+c2+" "+c3);*/
		
		/*for(int i=0;i<=61;i++){
			if(i<10){
				System.out.print((char)(i+'0'));
				if(i==9){
					System.out.println();
				}
			}
			else if(i<36){
				System.out.print((char)((i-10)+'A'));
				if(i==35){
					System.out.println();
				}
			}
			else {
				System.out.print((char)((i-36)+'a'));
			}
		}*/
		
		/*int a=5,b=2,c=9;
		
		int temp=a>b?a:b;
		
		int max=temp>c?temp:c;
		
		System.out.println(max);*/
		
		
		/*Scanner scana=new Scanner(System.in);
		System.out.println("����������a��");
		int a=scana.nextInt();
		
		Scanner scanb=new Scanner(System.in);
		System.out.println("����������b��");
		int b=scanb.nextInt();
		
		Scanner scanc=new Scanner(System.in);
		System.out.println("����������c��");
		int c=scanc.nextInt();
		
		int t=a>b?a:b;
		int m=t>c?t:c;
		System.out.println(m);*/
		
		/*int a=1,b;
		b=(a++)+(++a)+(a++)+a;
		System.out.println(a+","+b);*/
		
		
		int day=46;
		
//		System.out.println("����"+day%7);
		
		while(day<=7){
			System.out.println("����"+day);
			break;
		}
		
		while(day>7){
			int m=day/7;
			int n=day%7;
			System.out.println("��"+m+"��"+","+"����"+n);
			break;
		}
		
		int a=10;
		char d=(char)a;
//		byte e=(byte)129;���-127
//		char e='��'ֻ�ܴ��һ������;
		char b='A';
		int c=(int)b;//ת��֮����ʾA��ASCII��
		System.out.println(d);
		

		
		/*while(day%7==0){
			System.out.println("������");
			break;
		}
		while(day%7==1){
			System.out.println("����1");
			break;
		}
		while(day%7==2){
			System.out.println("����2");
			break;
		}
		while(day%7==3){
			System.out.println("����3");
			break;
		}
		while(day%7==4){
			System.out.println("����4");
			break;
		}
		while(day%7==5){
			System.out.println("����5");
			break;
		}
		while(day%7==6){
			System.out.println("����6");
			break;
		}*/
		
		
		/*System.out.println(com(9,6,4));	*/
	}
	
	/*static int com(int a,int b,int c){
		int t=a>b?a:b;
		int m=t>c?t:c;
		return m;
	}*/
}
