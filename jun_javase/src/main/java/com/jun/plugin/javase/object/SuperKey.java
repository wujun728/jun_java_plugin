package com.jun.plugin.javase.object;

public class SuperKey {
	public static void main(String[] args) {
		ChildClass cc=new ChildClass();
		cc.f();
	}
}
class FatherClass{
	public int value;
	public void f(){
		value=10;
		System.out.println("����ֵ"+this.value);
	}
}
class ChildClass extends FatherClass{
	public int value;//���˼̳и����value�����Լ����ⶨ����һ��value��
	public void f(){
		super.f();//ʹ��������еĸ�������Լ������Լ���f�������ı��������ࣩ��ֵ;
		value=200;
		System.out.println("����ֵ"+this.value);
		System.out.println(value);
		System.out.println(super.value);//�Ӹ���̳�����value��ֵ��
	}
}

/*
superָ���࣬�Ǹ��������
thisָ�����࣬�����������,ֻ�����ڷǾ�̬��������
��new����һ������ʱ�������������һ��this�����ã�ָ�������
���new�����Ķ���ʱһ���������Ļ�����ô�������������滹����һ��super���ã�ָ��ǰ����ĸ�����
*/









