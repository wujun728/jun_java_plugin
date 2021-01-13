package book.thread;

/**
 * 线程的互斥，主要展示同步方法与非同步方法的区别
 */
public class Synchronized {
	/**
	 * 帐号类
	 */
	static class Account{
		//共享资源， 钱数
		private double money = 1000.0d;
		/**
		 * 存钱	没有同步机制
		 * @param fFees
		 */
		public void nonSynDeposit(double fFees){
			System.out.println("Account nonSynDeposit begin! money = " + this.money + "; fFees = " + fFees);
			//存钱钱先等待300毫秒
			System.out.println("Account nonSynDeposit sleep begin!");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Account nonSynDeposit sleep end!");
			this.money = this.money + fFees;
			System.out.println("Account nonSynDeposit end! money = " + this.money);
		}
		/**
		 * 取钱	没有同步机制
		 * @param fFees
		 */
		public void nonSynWithdraw(double fFees){
			System.out.println("Account nonSynWithdraw begin! money = " + this.money + "; fFees = " + fFees);
			//取钱时先等待400毫秒
			System.out.println("Account nonSynWithdraw sleep begin!");
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Account nonSynWithdraw sleep begin!");
			this.money = this.money - fFees;
			System.out.println("Account nonSynWithdraw end! money = " + this.money);
		}
		/**
		 * 存钱	有同步机制
		 * @param fFees
		 */
		public synchronized void synDeposit(double fFees){
			System.out.println("Account synDeposit begin! money = " + this.money + "; fFees = " + fFees);
			//存钱钱先等待300毫秒
			System.out.println("Account synDeposit sleep begin!");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Account synDeposit sleep begin!");
			this.money = this.money + fFees;
			System.out.println("Account synDeposit end! money = " + this.money);
		}
		/**
		 * 取钱	有同步机制
		 * @param fFees
		 */
		public synchronized void synWithdraw(double fFees){
			System.out.println("Account synWithdraw begin! money = " + this.money + "; fFees = " + fFees);
			//取钱时先等待400毫秒
			System.out.println("Account synWithdraw sleep begin!");
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Account synWithdraw sleep end!");
			this.money = this.money - fFees;
			System.out.println("Account synWithdraw end! money = " + this.money);
		}
	}
	
	static class AccessThread extends Thread{
		//待访问的帐号对象
		private Account account = null;
		//访问帐号的方法
		private String method = "";

		public AccessThread(Account account, String method){
			this.method = method;
			this.account = account;
		}
		public void run(){
			//对不同的方法名参数调用不同的方法
			if (method.equals("nonSynDeposit")){
				account.nonSynDeposit(500.0);
			} else if (method.equals("nonSynWithdraw")){
				account.nonSynWithdraw(200.0);
			} else if (method.equals("synDeposit")){
				account.synDeposit(500.0);
			} else if (method.equals("synWithdraw")){
				account.synWithdraw(200.0);
			}
		}
	}

	public static void main(String[] args) {
		//待操作的帐号对象，所有操作都针对该帐号
		Account account = new Account();
		
		System.out.println("使用非同步方法时：");
		//非同步方法存钱的线程
		Thread threadA = new AccessThread(account, "nonSynDeposit");
		//非同步方法取钱的线程
		Thread threadB = new AccessThread(account, "nonSynWithdraw");
		threadA.start();
		threadB.start();
		//等待两线程运行结束
		try {
			threadA.join();
			threadB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//下面测试同步方法
		System.out.println();
		account = new Account();
		System.out.println("使用同步方法时：");
		threadA = new AccessThread(account, "synDeposit");
		threadB = new AccessThread(account, "synWithdraw");
		threadA.start();
		threadB.start();
	}
}
