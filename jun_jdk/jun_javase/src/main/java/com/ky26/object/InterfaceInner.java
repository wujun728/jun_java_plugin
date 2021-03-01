package com.ky26.object;

public class InterfaceInner {
	public static void main(String[] args) {
		
	}
}
interface Inter{
	void show();
	void show2();
}
class Outer31{
	static class Inner{
		
	}
	void method(){
		new Inner();
	}
	public static void show(Inter in){
		in.show();
		in.show2();
	}
	public static void main(String[] args) {
		show(new Inter(){
			public void show(){
				System.out.println("1");
			}
			public void show2(){
				System.out.println("2");
			}
		});
	}
}


