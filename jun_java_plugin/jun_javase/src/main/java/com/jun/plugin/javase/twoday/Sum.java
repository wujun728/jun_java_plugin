package com.jun.plugin.javase.twoday;

public class Sum {
	public static void main(String[] args) {
		
		double sum = 0;
        for(int i=1; i<=100; i++){
        	sum += 1.0/i;//��Χ����뷶ΧС�����㣬��ΧС���Զ�תΪ��Χ��ģ�double>int
        }
        System.out.println("sum = " + sum);	
        
	}
}
