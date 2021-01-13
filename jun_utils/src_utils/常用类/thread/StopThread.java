package book.thread;
/**
 * 停止线程
 */
public class StopThread {
	/**	线程对象	*/
	private ThreadA thread = new ThreadA();
	/**	自定义线程类 */
	class ThreadA extends Thread{
		//用一个boolean值标记线程是否需要运行。
		private boolean running = false;
		//覆盖了父类的start方法，
		public void start(){
			//将running置为ture，表示线程需要运行
			this.running = true;
			super.start();
		}
		public void run(){
			System.out.println("ThreadA begin!");
			int i=0; 
			try {
				//如果running为真，说明线程还可以继续运行
				while (running){
					System.out.println("ThreadA: " + i++);
					//sleep方法将当前线程休眠。
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
			}

			System.out.println("ThreadA end!");
		}
		public void setRunning(boolean running){
			this.running = running;
		}
	}
	/**
	 * 启动ThreadA线程
	 */
	public void startThreadA(){
		System.out.println("To start ThreadA!");
		thread.start();
	}
	/**
	 * 停止ThreadA线程
	 */
	public void stopThreadA(){
		System.out.println("To stop ThreadA!");
		thread.setRunning(false);
	}
	
	public static void main(String[] args) {
		StopThread test = new StopThread();
		//启动ThreadA线程
		test.startThreadA();
		//当前线程休眠一秒钟
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//停止ThreadA线程
		test.stopThreadA();
	}
}
