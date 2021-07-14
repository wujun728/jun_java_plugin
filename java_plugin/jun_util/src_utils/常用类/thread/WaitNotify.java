package book.thread;

import java.util.Vector;

/**
 * 线程间的协作
 */
public class WaitNotify {
	/**
	 * 打印信息的类，是一个线程。
	 */
	static class Printer extends Thread{
		Vector task = new Vector();
		boolean running = false;
		public void start(){
			this.running = true;
			super.start();
		}
		public void run(){
			try {
				System.out.println("Printer begin!");
				while (running){
					synchronized(this) {
						while ((task.size() == 0) && running){
							//如果任务列表为空，而且线程还允许运行，则等待任务
							System.out.println("wait begin!");
							//该线程进入等待状态，直到被其他线程唤醒
							wait();
							System.out.println("wait end!");
						}
					}
					if (running){
						System.out.println("print the task: " + task.remove(0));
					}
				}
				System.out.println("Printer end!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/**
		 * 添加待打印的任务
		 */
		public void addTask(String str){
			
			synchronized (this){
				this.task.add(str);
				//唤醒其他等待的线程
				System.out.println("addTask notify!");
				notify();
				//notifyAll();
			}
		}
		/**
		 * 停止线程
		 */
		public void stopPrinter(){
			this.running = false;
			synchronized (this){
				//唤醒其他等待的线程
				System.out.println("stopPrinter notify!");
				notify();
			}
		}
	}

	public static void main(String[] args) {
		Printer printer = new Printer();
		//启动打印线程
		printer.start();
		//添加任务
		try {
			Thread.sleep(200);
			for (int i=0; i<5; i++){
				//休眠200毫秒
				Thread.sleep(200);
				printer.addTask("The task " + i);
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		printer.stopPrinter();
	}
}