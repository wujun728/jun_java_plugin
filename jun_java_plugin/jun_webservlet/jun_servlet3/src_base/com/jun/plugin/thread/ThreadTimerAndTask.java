package com.jun.plugin.thread;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadTimerAndTask {

	public static void main(String[] args) {

		new Thread(new Runnable() {

			int i = 0;

			@Override
			public void run() {
				while (true) {
					System.out.println(i++);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();

		/* 起始间隔,阶段间隔 */

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				System.out.println("delay 1000");

			}

		}, 2000, 1000);

	}

}
