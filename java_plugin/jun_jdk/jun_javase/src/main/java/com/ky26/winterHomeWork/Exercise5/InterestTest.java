package com.ky26.winterHomeWork.Exercise5;

public class InterestTest {
	public static void main(String[] args) {
		Interest.setRate(0.0225);
		//Interest.setTax(0.2);
		System.out.println(Interest.getTax());
		Interest i1=new Interest(10000,5);
		System.out.println(i1.showSum());
	}
}
class Interest{
	private static double rate;
	private static double tax;
	private double amount;
	private int year;
	Interest(){
		
	}
	Interest(double amount,int year){
		this.amount=amount;
		this.year=year;
	}
	static void setRate(double rate) {
		Interest.rate=rate;
	}
	static void setTax(double tax) {
		Interest.tax=tax;
	}
	static double getTax() {
		return Interest.tax;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	double showSum() {
		double sum=1;
		if(Interest.getTax()==0) {
			for(int i=0;i<year;i++) {
				sum*=(1+rate);
			}
		}//无利息税
		else {
			for(int i=0;i<year;i++) {
				sum*=(1+rate*Interest.getTax());
			}
		}//有利息税
		return getAmount()*sum;
	}
}
