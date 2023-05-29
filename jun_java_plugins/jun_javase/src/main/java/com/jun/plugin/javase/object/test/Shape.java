package com.jun.plugin.javase.object.test;

public abstract class Shape {
	private String color;
	
	void setColor(String color){
		this.color=color;
	}
	String getColor(){
		return color;
	}
	
	abstract int getPerimeter();
	abstract void getShap();
}
