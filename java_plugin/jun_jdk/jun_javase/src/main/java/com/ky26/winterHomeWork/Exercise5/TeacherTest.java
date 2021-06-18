package com.ky26.winterHomeWork.Exercise5;

public class TeacherTest {
	public static void main(String[] args) {
		Teacher.setCoefficient(1.2);
		Teacher t1=new Teacher(10);
		Teacher t2=new Teacher(20);
		System.out.println(t1.courseCompute());
		System.out.println(t2.courseCompute());
	}
}
class Teacher{
	private double amount;
	private static double coefficient;
	Teacher(){
		
	}
	Teacher(double amount){
		this.amount = amount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public static double getCoefficient() {
		return coefficient;
	}
	public static void setCoefficient(double coefficient) {
		Teacher.coefficient = coefficient;
	}
	double courseCompute() {
		return (getAmount()*getCoefficient());
	}
	
}
