package com.yzm.common.checkthread;


public abstract class BaseThread implements Runnable {

	public void run() {
		beforeRun();
		while (true) {
			try {
				this.process();
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		afterRun();
	}

	public abstract void beforeRun();

	public abstract void process();

	public abstract void afterRun();

}
