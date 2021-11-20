    package com.jun.plugin.javase.winterHomeWork;

public class DiceTest {
	public static void main(String[] args) {
		Dice d1=new Dice();
		int a=d1.throwDice();
		int b=d1.throwDice();
		if(a+b==7) {
			System.out.println("��Ӯ��");
		}
		else {
			System.out.println("������");
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