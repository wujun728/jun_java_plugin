package com.ky26.collectionn;

import java.util.Map;

public class Person1 implements Comparable{
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Person1(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	Person1(){
		
	}
	
	public void order(Map map,int id){
		System.out.println("ÄãµãµÄ²ËÊÇ£º"+map.get(id));
	}
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person1 other = (Person1) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Person1 [name=" + name + ", age=" + age + "]";
	}
	@Override
	public int compareTo(Object o) {
		Person1 p1=(Person1) o;
		int result=this.age>p1.getAge()?1:(this.age==p1.getAge()?0:-1);
		return result;
	}
	
	
}
