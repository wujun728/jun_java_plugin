package com.jun.plugin.demo.chap03.sec03;

public class People4 {

	// ��������
	int speak(String name,int age,String ...hobbies){
		System.out.println("�ҽ�"+name+",�ҽ���"+age+"����");
		System.out.print("�ҵİ��ã�");
		for(String hobby:hobbies){
			System.out.print(hobby+" ");
		}
		// ��ȡ���õĳ���
		int totalHobbies=hobbies.length;
		return totalHobbies;
	}
	
	public static void main(String[] args) {
		People4 zhangsan=new People4();
		int n=zhangsan.speak("����",23,"��Ӿ","����","����");
		System.out.println("\n��"+n+"������");
	}
}
