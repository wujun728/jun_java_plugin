package com.ky26.object.test;

public abstract class Empolyee {
	private String name;
	private String Id;
	private double salary;
	public Empolyee(String name, String Id, double salary) {
		this.name = name;
		this.Id = Id;
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String toString() {
		return "Empolyee [name=" + name + ", Id=" + Id + ", salary=" + salary+ "]";
	}
	abstract void work();
	
}
