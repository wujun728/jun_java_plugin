package com.jun.plugin.javase.twoday;

public class HomeWorkDate {
	public static void main(String[] args) {
		
//		int year=(int)(Math.random()*1000+2000);
		int year=2016;
		int mouth=9;
		int date=15;
//		int mouth=(int)(Math.random()*12+1);
//		int date=(int)(Math.random()*31+1);
		
		int i=mouth;//�����С�·ݣ�����31��С��30��
		if(i==1){
			i=0;
		}
		else if(i==2){
			i=1;
		}
		else if(i==3){
			i=1;
		}
		else if(i==4){
			i=2;
		}
		else if(i==5){
			i=2;
		}
		else if(i==6){
			i=3;
		}
		else if(i==7){
			i=3;
		}
		else if(i==8){
			i=4;
		}
		else if(i==9){
			i=5;
		}
		else if(i==10){
			i=5;
		}
		else if(i==11){
			i=6;
		}
		else if(i==12){
			i=6;
		}
		
		System.out.println(year+"��"+mouth+"��"+date+"��");
		if((year%4==0&&year%100!=0)||(year%400==0)){
			
			if(mouth<=2){
				System.out.println((mouth-1)*30+date+i);
			}
			else{
				System.out.println((mouth-1)*30+date+i-1);
				System.out.println("����");
			}
//			����
		}
		else{
			if(mouth<=2){
				System.out.println((mouth-1)*30+date+i);
			}
			else{
				System.out.println((mouth-1)*30+date+i-2);
				System.out.println("ƽ��");
			}
//			ƽ��
		}
	}
}
