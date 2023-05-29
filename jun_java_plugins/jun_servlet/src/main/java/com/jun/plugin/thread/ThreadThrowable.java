package com.jun.plugin.thread;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadThrowable {

	public static void main(String[] args) {

		try {

			timeRun(new Runnable() {
				@Override
				public void run() {

					System.out.println("睡觉5秒");

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}, 3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.err.println("in main " + e.getMessage());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void timeRun(final Runnable r, long timeout, TimeUnit unit)
			throws Throwable {

		ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(1);
		RethrowableTask task = new RethrowableTask(r);
		final Thread taskThread = new Thread(task);
		taskThread.start();
		ses.schedule(new Runnable() {

			@Override
			public void run() {
				taskThread.interrupt();
			}

		}, timeout, unit);
		taskThread.join(3000);
		task.reThrow();
		System.out.println("主线程退出");
	}

}

class RethrowableTask implements Runnable {

	private volatile Throwable t;

	private Runnable r;

	public RethrowableTask(Runnable r) {
		this.r = r;
	}

	@Override
	public void run() {

		try {
			r.run();
		} catch (Throwable t) {
			this.t = t;
		}

	}

	public void reThrow() throws Throwable {
		System.out.println("调用了reThrow方法");
		if (t != null) {
			System.out.println("异常不为空");
			throw t;
		}
	}

}