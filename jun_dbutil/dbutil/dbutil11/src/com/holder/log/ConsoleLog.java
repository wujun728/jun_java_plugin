package com.holder.log;

import java.util.Date;

public abstract class ConsoleLog {
	public static void debug(String content) {
		System.out.println(new Date() + " | debug | " + content);
	}
}
