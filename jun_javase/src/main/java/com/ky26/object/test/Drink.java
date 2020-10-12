package com.ky26.object.test;

public abstract class Drink {
	private String taste;
	private String capacity;
	private int price;
	Drink(String taste,String capacity,int price){
		this.taste=taste;
		this.capacity=capacity;
		this.price=price;
	}
	
	void setTaste(String taste){
		this.taste=taste;
	}
	String getTaste(){
		return taste;
	}
	void setCapacity(String capacity){
		this.capacity=capacity;
	}
	String getCapacity(){
		return capacity;
	}
	void setPrice(int price){
		this.price=price;
	}
	int getPrice(){
		return price;
	}
	
	void showProperty(){
		System.out.println(getTaste()+","+getCapacity()+","+getPrice());
	}
	
	abstract void showName();
}
