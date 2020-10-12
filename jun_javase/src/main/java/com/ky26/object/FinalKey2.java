package com.ky26.object;

public class FinalKey2 {
	public static void main(String[] args) {
		Demo2 d1=new Demo2();
		d1.show();
		
		/*Demo2 d2=new Demo2();
		d2.show();*/
	}
}
class Demo2{
	
	
	static Demo2 d=new Demo2();
	static int n=0;
	
	Demo2(){
		n++;
	}
	void show(){
		System.out.println(n);
	}
}
