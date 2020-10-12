package com.ky26.object;

public class Object1 {
	public static void main(String[] args) {
		
		/*Car c1=new Car();
		c1.showCar();
		
		c1.color="black";
		c1.num=5;
		c1.showCar();
		
		Car c2=c1;
		c2.num=6;
		c2.color="blue";
		c2.showCar();
		
		Car c3=new Car();
		c3.num=3;
		c3.color="yellow";
		c3.showCar();
		
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);*/
		
		
		List(1,2,3);
		
	}
	
	static void List(int...intArray){
		for(int i=0;i<intArray.length;i++){
			if(i==intArray.length-1){
				System.out.print(intArray[i]);
			}
			else{
				System.out.print(intArray[i]+",");
			}
		}
	}
	
	static void chang(Car c){
		c.color="red";
		c.num=10;
		c.showCar();
	}//Car c，类C的一个引用
}
class Car{
	int num;
	String color;
	public void showCar(){
		System.out.println(num+"------"+color);
	}
	
	
	public Car(){
		this.num=4;
		this.color="white";
	}
}//一个类中只能有一个public
