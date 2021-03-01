package com.ky26.winterHomeWork.Exercise8;

public class BankAccountTest {
	public static void main(String[] args) {
		BankAccount b1=new BankAccount();
		/*BankAccount b2=new BankAccount();
		BankAccount b3=new BankAccount();*/
		b1.saveMoney(500);
		try {
			b1.getMoney(100);
		} catch (InsufficientFundsException e) {
			e.printStackTrace();
		}
//		System.out.println(b1.getAccountNum());
		/*System.out.println(b2.getAccountNum());
		System.out.println(b3.getAccountNum());*/
		System.out.println("”‡∂Ó£∫"+b1.getBalance());
	}
}
class BankAccount{
	private static int count=0;
	private int accountNum;
	private int balance;
	public BankAccount() {
		count++;
		accountNum=count;
		balance=0;
	}
	public int getAccountNum() {
		return this.accountNum;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	public void saveMoney(int money) {
		 this.balance+=money;
	}
	public void getMoney(int money) throws InsufficientFundsException{
		if(this.balance<money) {
			throw new InsufficientFundsException("”‡∂Ó≤ª◊„");
		}
		else {
			this.balance-=money;
		}
	}
	public int getBalance() {
		return this.balance;
	}
}
class InsufficientFundsException extends Exception{
	public InsufficientFundsException(String exceptionMes) {
		super(exceptionMes);
	}
}