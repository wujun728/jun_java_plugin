package com.ky26.object.test;

public class Triangle extends Shape {
	private int longA;
	private int wideB;
	private int heightC;
	
	Triangle(int longA,int wideB,int heightC){
		this.longA=longA;
		this.wideB=wideB;
		this.heightC=heightC;
		isTriangle();
	}
	boolean isTriangle() {
		if((this.longA+this.wideB>this.heightC&&this.longA+this.heightC>this.wideB&&this.wideB+this.heightC>this.longA)&&(this.longA-this.wideB<this.heightC&&this.longA-this.heightC<this.wideB&&this.wideB-this.heightC<this.longA)) {
			return true;
		}
		else {
			return false;
		}
	}
	void setLongA(int longA){
		this.longA=longA;
	}
	int getLongA(){
		return longA;
	}
	void setWideB(int wideB){
		this.wideB=wideB;
	}
	int getWideB(){
		return wideB;
	}
	void setHeightC(int heightC){
		this.heightC=heightC;
	}
	int getHeightC(){
		return heightC;
	}
	
	
	int getPerimeter(){
		return (getLongA()+getWideB()+getHeightC());
	}
	void getShap(){
		System.out.println(getColor()+"Èý½ÇÐÎ");
	}
}

