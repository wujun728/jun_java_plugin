package cn.org.rapid_framework.generator.util;

import java.io.PrintStream;

public class GLogger {
	public static final int TRACE = 60;
	public static final int DEBUG = 70;
	public static final int INFO = 80;
	public static final int WARN = 90;
	public static final int ERROR = 100;

	public static int logLevel = INFO;
	public static PrintStream out = System.out;
	public static PrintStream err = System.err;

	public static void trace(String s) {
		if (logLevel <= TRACE)
			out.println("[Generator TRACE] " + s);
	}
	
	public static void debug(String s) {
		if (logLevel <= DEBUG)
			out.println("[Generator DEBUG] " + s);
	}

	public static void info(String s) {
		if (logLevel <= INFO)
			out.println("[Generator INFO] " + s);
	}

	public static void warn(String s) {
		if (logLevel <= WARN)
			err.println("[Generator WARN] " + s);
	}

	public static void warn(String s, Throwable e) {
		if (logLevel <= WARN) {
			err.println("[Generator WARN] " + s + " cause:"+e);
			e.printStackTrace(err);
		}
	}

	public static void error(String s) {
		if (logLevel <= ERROR)
			err.println("[Generator ERROR] " + s );
	}

	public static void error(String s, Throwable e) {
		if (logLevel <= ERROR) {
			err.println("[Generator ERROR] " + s + " cause:"+e);
			e.printStackTrace(err);
		}
	}
	
	public static boolean perf = false;
    public static void perf(String s) {
        if(perf)
            out.println("[Generator Performance] " + s);
    }
	
	public static void println(String s) {
		if (logLevel <= INFO) {
			out.println(s);
		}
	}
	
}
