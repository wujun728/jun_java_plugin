package com.yc.reflectTest;

public class User {
	private int age;
	private String name;
	private String sex;
	int hight;
	int weight;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getHight() {
		return hight;
	}
	public void setHight(int hight) {
		this.hight = hight;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public User() {
		super();
	}
	public User(int age, String name, String sex, int hight, int weight) {
		super();
		this.age = age;
		this.name = name;
		this.sex = sex;
		this.hight = hight;
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + ", sex=" + sex
				+ ", hight=" + hight + ", weight=" + weight + "]";
	}
	
	
}
