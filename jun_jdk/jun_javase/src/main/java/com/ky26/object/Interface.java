package com.ky26.object;

public class Interface {
	public static void main(String[] args) {
		//useUSB(new Mouse());//多态中，使用一个方法根据传入对象的不同而实现不同的方法，，传入时的格式就是这样的？？？以某个对象为参数调用某个方法
		//Mouse m1=new Mouse();
		//m1.print();
		
		new USB(){
			public void print(){
				System.out.println(1);
			}
		}.print();//使用匿名内部类，，与注释部分等价
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

