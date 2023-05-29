package com.jun.plugin.javase.oneday;

public class HomeWork {
	public static void main(String[] args) {
		for(int man=0;man<=12;man++){
			for(int woman=0;woman<=18;woman++){
				for(int child=0;child<=36-man-woman;child++){
					if(3*man+2*woman+child/2==36&&man+woman+child==36&&child%2==0){
						System.out.println("����"+man+"Ů��"+woman+"С��"+child);
					}
				}
			}
		}
		System.out.println();
		
		int count=0;
		for(int cock=0;cock<=20;cock++){
			for(int hen=0;hen<=33;hen++){
				for(int chick=0;chick<=200;chick++){
					if(cock*5+hen*3+chick/2==100&&cock+hen+chick==100&&chick%2==0){
						System.out.println("����"+cock+"ĸ��"+hen+"С��"+chick);
					}
				}
			}
			count++;
		}
		System.out.println("���д���Ϊ"+count);
		System.out.println();
	/*	
		int a=378;
		System.out.println(a/100+" "+a/10%10+" "+a%10);*/
		
		
		for(int i=100;i<1000;i++){
			int a=i/100;
			int b=i/10%10;
			int c=i%10;
			if(a*a*a+b*b*b+c*c*c==i){
				System.out.println("ˮ�ɻ���"+i);
			}
		}
		
		
	}
}
