package com.jun.plugin.javase.object;

import java.util.Arrays;

public class Test2 {
	public static void main(String[] args) {
		int a=3;
		int b=4;
		change(a,b);//��ջ����ִ����ϡ�����ջ
		System.out.println("a:"+a);
		System.out.println("b:"+b);//���ݵ�ֵ����Ӱ��ԭֵ
		
		int[] count={1,2,3,4,5};
		change(count);//����counts��count����һ����ַ��ͬʱָ����еĶ��󣬣��ı�������һ����ֵ����һ��Ҳ��ı䣻
		System.out.println("���÷���֮������飺"+Arrays.toString(count));//���õ�ֵ�ı���ԭֵ
	}
	static void change(int i,int j){
		int temp=i;
		i=j;
		j=temp;
	}
	
	public static void change(int[] counts){
		counts[0]=6;
		System.out.println("count�ĵ�һ������Ԫ���ǣ�"+counts[0]);
	}
}
/*��Java�в������ݶ����и����ģ�����˵��ֵ���ݣ������ǽ���������һ���ٽ��д��ݣ������������͵�ֵ�Ǹ��ƣ����������ݽ��в�������˲���ı�ԭֵ��
��һ�ֽ����ǣ������������ʹ洢��ջ�У������÷�������ʱ������ջ�����ܸı�ԭֵ���Ҳ���ԭֵ����
����Ĵ����Ǵ��ݵ�ַ���Ƕ�������ã�������ַָ����ڴ��е�ͬһ�����ݣ��ı�������һ���������������仯��*/


