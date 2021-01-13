package book.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器
 * 在应用开发中，经常需要一些周期性的操作，比如每5分钟检查一下新邮件等。
 * 对于这样的操作最方便、高效的实现方式就是使用java.util.Timer工具类。
 */
public class UsingTimer {
	/**
	 * 我的任务类，继承TimerTask
	 * TimerTask是一个抽象类，必须实现它的抽象方法run()
	 */
	static class MyTask extends TimerTask{
		private int taskID = 0;
		public MyTask(int id){
			this.taskID = id;
		}
		public void run(){
			System.out.println("run MyTask-" + this.taskID 
					+ " at time: " + System.currentTimeMillis());
		}
	}

	public static void main(String[] args) {
		Timer timer = new Timer();
		TimerTask myTask1 = new MyTask(1);
		//200ms后执行myTask1
		timer.schedule(myTask1, 200);
		//300ms后执行myTask2，并且以后每个500ms再执行一次myTask2
		TimerTask myTask2 = new MyTask(2);
		timer.schedule(myTask2, 300, 500);
		//在指定时间（一秒钟之后）执行myTask3
		TimerTask myTask3 = new MyTask(3);
		Date date = new Date(System.currentTimeMillis() + 1000);
		timer.schedule(myTask3, date);
		try {
			//等待5秒钟
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//终止定时器，并取消定时器中的任务
		timer.cancel();
		System.out.println("timer canceled!");
	}
}
