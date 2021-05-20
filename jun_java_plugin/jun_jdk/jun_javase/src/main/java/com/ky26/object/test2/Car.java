package com.ky26.object.test2;

public class Car {
	private String type;
	private String color;
	private int num;
	
	Car(String type,String color,int num){
		this.type=type;
		this.color=color;
		this.num=num;
	}
	
	void setType(String type){
		this.type=type;
	}
	String getType(){
		return type;
	}
	void setClor(String color){
		this.color=color;
	}
	String getColor(){
		return color;
	}
	void setNum(int num){
		this.num=num;
	}
	int getNum(){
		return num;
	}
	public String toString(){
		return "Car [type=" + type + ", color=" + color + ", num=" + num + "]";
	}

	public boolean equals(Object obj){
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		
		Car other=(Car)obj;
		if(color==null){
			if(other.color!=null)
				return false;
		}else if(!color.equals(other.color))
			return false;
		
		if(num!=other.num)
			return false;
		
		if(type==null){
			if(other.type!=null)
				return false;
		}else if(!type.equals(other.type))
			return false;
		
		return true;
	}

	public static void main(String[] args) {
		Car c1=new Car("大众","黑色",120);
		Car c2=new Car("大众","黑色",120);
		
		/*System.out.println(c1.getClass());
		System.out.println(c2.getClass());*/
		
		System.out.println(c1.equals(c2));
		
		/*Class clazz1=c1.getClass();
		Class clazz2=c2.getClass();*/
		/*System.out.println(clazz1==clazz2);
		System.out.println(c1.getClass());
		System.out.println(c1.toString());
		System.out.println(c1.hashCode());
		System.out.println(Integer.toHexString(c1.hashCode()));*/
	}
}
