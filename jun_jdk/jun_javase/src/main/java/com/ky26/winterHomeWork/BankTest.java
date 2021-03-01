package com.ky26.winterHomeWork;

public class BankTest {
	public static void main(String[] args) {
		Bank b1=new Bank();
		b1.setIncome(100);
		b1.setExpenditure(30);
//		b1.showBalance();
		System.out.println(b1.getBalance());
		Bank b2=new Bank(200,100);
		b2.showBalance();
		
	}
}
class Bank{
	private int income;
	private int expenditure;
	private int balance;
	Bank(){
		
	}
	Bank(int income,int expenditure){
		this.income=income;
		this.expenditure=expenditure;
	}
	void setIncome(int income) {
		this.income=income;
	}
	int getIncome() {
		return this.income;
	}
	void setExpenditure(int expenditure) {
		this.expenditure=expenditure;
	}
	int getExpenditure() {
		return this.expenditure;
	}
	int getBalance() {
		return (getIncome()-getExpenditure());
	}
	void showBalance() {
		System.out.println("’Àªß”‡∂Ó£∫"+getBalance());
	}
}