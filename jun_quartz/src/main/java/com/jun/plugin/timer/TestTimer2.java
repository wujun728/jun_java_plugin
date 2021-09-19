package com.jun.plugin.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TestTimer2 {
	Timer timer;

	class ReminderTask extends TimerTask {
		@Override
		public void run() {
			print("Welcome!");
			timer.cancel();
		}
	}

	public TestTimer2(Date time) {
		timer = new Timer();
		timer.schedule(new ReminderTask(), time);
	}

	private static void print(String s) {
		System.out.println(s);
	}

	public static void main(String args[]) {
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.HOUR_OF_DAY, 11);
		calender.set(Calendar.MINUTE, 54);
		calender.set(Calendar.SECOND, 00);
		Date time = calender.getTime();
		//System.out.println(time);
		print("Hello!");
		new TestTimer2(time);
		print("Good!");
	}
}