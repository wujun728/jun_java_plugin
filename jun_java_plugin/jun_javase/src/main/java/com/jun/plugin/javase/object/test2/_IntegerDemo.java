package com.jun.plugin.javase.object.test2;

public class _IntegerDemo {
	public static void main(String[] args) {
		int a=12;
		Integer b;
		b=a;
		a=b;//����һ����ʽ�ͱ���ʽ����λ�ûᱨ��
		System.out.println(b==a);
		System.out.println(Integer.toString(a));
		
		String str=Integer.toString(456);
		String str1=Integer.toBinaryString(456);
		String str2=Integer.toHexString(456);
		String str3=Integer.toOctalString(456);
		System.out.println("ʮ����"+str);
		System.out.println("������"+str1);
		System.out.println("ʮ������"+str2);
		System.out.println("�˽���"+str3);
		
		String strArr[]={"12","22","32","42"};
		int sum=0;
		for(int i=0;i<strArr.length;i++){
			int myInt=Integer.parseInt(strArr[i]);
			sum+=myInt;
		}
		System.out.println(sum);
		
		String str11="21";
		System.out.println(Integer.valueOf(str11));
		
	}
}
