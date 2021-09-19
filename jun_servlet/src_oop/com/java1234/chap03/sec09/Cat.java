package com.java1234.chap03.sec09;

/**
 * 定义个Cat类，继承自Animal
 * @author user
 *
 */
public class Cat extends Animal{

	private String address;
	
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 子类无参构造方法
	 */
	public Cat(){
		super();
		System.out.println("子类无参构造方法");
	}
	
	/**
	 * 子类有参数构造方法
	 * @param name
	 * @param age
	 */
	public Cat(String name,int age,String address){
		super(name,age);
		this.address=address;
		System.out.println("子类有参数构造方法");
	}
	
	
	/**
	 * 重写父类的say方法
	 */
	public void say(){
		super.say();
		System.out.println("我是一只猫，我叫："+this.getName()+",我的年龄是："+this.getAge()+",我的地址是："+this.getAddress());
	}
	
	public static void main(String[] args) {
		Cat cat=new Cat("Mini",2,"火星");
		/*cat.setName("Mini");
		cat.setAge(2);*/
		cat.say();
	}
}
