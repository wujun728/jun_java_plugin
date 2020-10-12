package com.ky26.object;

public class Test6 {
	public static void main(String[] args) {
		
		Outer2.Inner2 o1=new Outer2().new Inner2();//注释部分与本声明同价
		o1.show();
		
	}
}
class Outer2{
	/*Inner2 in=new Inner2();
	void print(){
		in.show();
	}*/
	
	class Inner2{
		int n=12;
		void show(){
			System.out.println(n);
		}
	}
}


