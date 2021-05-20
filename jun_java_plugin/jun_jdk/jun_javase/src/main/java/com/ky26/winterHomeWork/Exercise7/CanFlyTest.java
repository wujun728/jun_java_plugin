package com.ky26.winterHomeWork.Exercise7;

public class CanFlyTest {
	public static void main(String[] args) {
		Aircraft a1=new Aircraft();
		Bird b1=new Bird();
		makeFly(a1);
		makeFly(b1);
	}
	static void makeFly(CanFly clazz) {
		System.out.println(clazz+" ÄÜ·É");
	}
}
interface CanFly{
	public void fly();
}
class Aircraft implements CanFly{
	public void fly() {
		
	}
}
class Bird implements CanFly{
	public void fly() {
		
	}
}
