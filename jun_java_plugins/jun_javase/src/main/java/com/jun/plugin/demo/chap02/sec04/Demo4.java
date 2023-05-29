package com.jun.plugin.demo.chap02.sec04;

public class Demo4 {

	public static void main(String[] args) {
		// && ��  ǰ���������������붼��true�ŷ���true,���򷵻�false
		boolean b1=(5<3)&&(4>5);
		System.out.println("b1="+b1);
		
		// & ����·��
		boolean b2=(5<3)&(4>5);
		System.out.println("b2="+b2);
		
		// һ�㶼��&& ��·  
		// ԭ��Ч�ʸ�
		
		// || �� ֻҪ��������������һ����true���ͷ���true�����򷵻�false
		boolean b3=(2<3)||(4>5);
		System.out.println("b3="+b3);
		
		// | ����· ��
		boolean b4=(2<3)|(4>5);
		System.out.println("b4="+b4);
		
	    // ! �� �����������Ϊtrue������false�����򣬷���true
		boolean b5=!(3<4);
		System.out.println("b5="+b5);
		
		// ^ ��� ������������������ͬʱ����true������false
		boolean b6=(5>4)^(4>5);
		System.out.println("b6="+b6);
	}
}
