    package com.ky26.winterHomeWork;

public class DiceTest {
	public static void main(String[] args) {
		Dice d1=new Dice();
		int a=d1.throwDice();
		int b=d1.throwDice();
		if(a+b==7) {
			System.out.println("ÄãÓ®ÁË");
		}
		else {
			System.out.println("ÄãÊäÁË");
		}
	}
}
class Dice{
	private int count;
	public int throwDice() {
		this.count=(int)(Math.random()*6+1);
		System.out.println(this.count);
		return count;
	}
}