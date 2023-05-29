package com.jun.plugin.javase.object;

public class Rectangle {
	int a;
	int b;
	
	public Rectangle(){
		this.a=5;
		this.b=6;
	}
	
	public int area(){
		return a*b;
	}
	
	public static void main(String[] args) {
		Rectangle test=new Rectangle();
		System.out.println("������"+test.area());
		
		Trectangle test2=new Trectangle();
		test2.a=12;
		test2.b=1;
		System.out.println("������"+test2.area());
	}
}



class Trectangle extends Rectangle{
	public Trectangle(){
		super();//ֻ���������๹�췽����ĵ�һ��
		super.area();
	}
	
	public int area(){
		return a*b*10;
	}
}

