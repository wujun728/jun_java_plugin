package com.jun.plugin.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
	Timer timer;

	class ReminderTask extends TimerTask {
		public void run() {
			print("Welcome!");
			timer.cancel();
		}
	}
	
	public TestTimer(int seconds) {
		timer = new Timer();
		timer.schedule(new ReminderTask(), seconds * 1000);
	}

	private static void print(String s) {
		System.out.println(s);
	}

	public static void main(String args[]) {
		print("Hello!");
		new TestTimer(3);
		print("Good!");
	}
}
