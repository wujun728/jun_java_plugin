package com.jun.plugin.demo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



public class TestReadWriteLock {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TestReadWriteLock tw = new TestReadWriteLock();
		Account account = new Account("6222022603002001666", 100000);
		final ReadWriteLock rwl = new ReentrantReadWriteLock(); //所有对象公用一把锁
		User user1 = tw.new  User("张浩", account, -1500,false,rwl);
		User user2 = tw.new  User("王五", account, -1600,false,rwl);
		User user3 = tw.new  User("宋柳", account, -1700,false,rwl);
		User user4 = tw.new  User("杨洋", account, -1800,false,rwl);
		User user5 = tw.new  User("张得标", account, -1900,true,rwl);
		
		ExecutorService es = Executors.newFixedThreadPool(2);
		
			es.execute(user1);
			es.execute(user2);
			es.execute(user3);
			es.execute(user4);
			es.execute(user5);
		
	}
	
	
	class User implements Runnable{
		private String name;
		private Account account;
		private int amount;
		boolean ischeck;
		private ReadWriteLock rwl;

		public User(String name, Account account, int amount,boolean ischeck,ReadWriteLock rwl) {
			this.name = name;
			this.account = account;
			this.amount = amount;
			this.ischeck = ischeck;
			this.rwl = rwl;
		}


		@Override
		public void run() {
			while(true){
			if(ischeck){
				rwl.readLock().lock();
				System.out.println(name+"--正在查询--，当前余额："+account.getBalance());
				rwl.readLock().unlock();
			}else{
				rwl.writeLock().lock();
				System.out.println(name+"--正在操作账户："+account.getAccountId()+"操作金额为："+amount+"当前金额为"+account.getBalance());
				account.setBalance(account.getBalance()+amount);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(name+"--操作账户："+account.getAccountId()+"成功，操作金额为："+amount+"当前金额为"+account.getBalance());
				rwl.writeLock().unlock();
			}
			}
		}
	}
	
	static class Account{
		
		private String accountId;
		
		private int balance;
		
		
		public Account(String accountId, int i) {
			super();
			this.accountId = accountId;
			this.balance = i;
		}
		
		public String getAccountId() {
			return accountId;
		}
		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}
		public int getBalance() {
			return balance;
		}
		public void setBalance(int balance) {
			this.balance = balance;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "MyCount{" +  
            "oid='" + accountId + '\'' +  
            ", cash=" + balance +  
            '}';  
		}
		
	}
}
