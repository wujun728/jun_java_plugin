package com.jun.plugin.demo.chap03.sec03;

public class People3 {

	// �βΣ����
	void speak(String name,int age){
		System.out.println("�ҽ�"+name+",�ҽ���"+age+"����");
	}
	
	public static void main(String[] args) {
		People3 zhangsan=new People3();
		zhangsan.speak("����",23);
	}
}
