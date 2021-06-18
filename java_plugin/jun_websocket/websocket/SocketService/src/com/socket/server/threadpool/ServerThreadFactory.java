package com.socket.server.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerThreadFactory implements ThreadFactory{
	
	private final static String PERFIX = "thread-";
	
	private final AtomicInteger atomicInteger = new AtomicInteger(0);

	@Override
	public Thread newThread(Runnable r) {
		String name = PERFIX + atomicInteger.incrementAndGet();
		Thread thread = new Thread(r,name);
		thread.setDaemon(false);
		return thread;
	}

}
