package com.ky26.object;

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
		System.out.println("父类结果"+test.area());
		
		Trectangle test2=new Trectangle();
		test2.a=12;
		test2.b=1;
		System.out.println("子类结果"+test2.area());
	}
}



class Trectangle extends Rectangle{
	public Trectangle(){
		super();//只能用在子类构造方法体的第一行
		super.area();
	}
	
	public int area(){
		return a*b*10;
	}
}

