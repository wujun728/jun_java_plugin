package com.jun.plugin.javase.object;

public class Interface {
	public static void main(String[] args) {
		//useUSB(new Mouse());//��̬�У�ʹ��һ���������ݴ������Ĳ�ͬ��ʵ�ֲ�ͬ�ķ�����������ʱ�ĸ�ʽ���������ģ�������ĳ������Ϊ��������ĳ������
		//Mouse m1=new Mouse();
		//m1.print();
		
		new USB(){
			public void print(){
				System.out.println(1);
			}
		}.print();//ʹ�������ڲ��࣬����ע�Ͳ��ֵȼ�
	}
	
	public static void useUSB(USB u){}
}

interface USB{
	int a=15;
	void print();
}
/*class Mouse implements USB{
	void show(){
		System.out.println(a);
	}
	public void print(){
		System.out.println(a);
	}
}*/

