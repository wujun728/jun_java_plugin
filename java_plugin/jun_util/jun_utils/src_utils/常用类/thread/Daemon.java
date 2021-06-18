package book.thread;

/**
 * Daemon(守护)线程 
 * Daemon线程区别一般线程之处是： 
 * 只有虚拟机中的用户线程（非Daimon线程）全部结束，Daemon线程就会立即结束,并且也不会调用finally里的语句。
 * daemon线程所产生的所有线程都是daemon的
 */
public class Daemon {

	static class MainThread extends Thread {

		public void run() {
			System.out.println("MainThread is daemon? " + this.isDaemon());
			System.out.println("MainThread begin!");
			//启动子线程
			Thread sub1 = new SubThread();
			//sub1线程为守护线程
			sub1.setDaemon(true);
			sub1.start();
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("MainThread" + " finally");
			}
			System.out.println("MainThread end!");
		}
	}

	static class SubThread extends Thread {

		public void run() {
			System.out.println("SubThread is daemon? " + this.isDaemon());
			System.out.println("SubThread begin!");
			int i = 0;
			try {
				while (i < 10) {
					System.out.println("SubThread  " + i++);
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("SubThread finally");
			}
			System.out.println("SubThread end!");
		}
	}

	public static void main(String[] args) {
		System.out.println("Main begin!");
		//默认情况下mainThread是普通线程
		Thread mainThread = new MainThread();
		//启动mainThread线程
		mainThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("Main end!");
	}
}