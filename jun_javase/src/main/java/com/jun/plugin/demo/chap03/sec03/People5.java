package com.jun.plugin.demo.chap03.sec03;

/**
 * ��Χ��
 * @author caofeng
 *
 */
class SanWei{
	int b; // ��Χ
	int w; // ��Χ
	int h; // ��Χ
}

public class People5 {

	/**
	 * ����Χ
	 * @param age ����
	 * @param sanWei 
	 */
	void speak(int age,SanWei sanWei){
		System.out.println("�ҽ���"+age+"����,�ҵ���Χ�ǣ�"+sanWei.b+","+sanWei.w+","+sanWei.h);
		age=24;
		sanWei.b=80;
	}
	
	public static void main(String[] args) {
		People5 xiaoli=new People5();
		int age=23;
		SanWei sanWei=new SanWei();
		sanWei.b=90;
		sanWei.w=60;
		sanWei.h=90;
		// age���ݵ���ֵ��sanWei���ݵ�������(��ַ),c���ָ��
		xiaoli.speak(age,sanWei);
		System.out.println(age);
		System.out.println(sanWei.b);
	}
}
