package com.socket.server.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The thread pool deal with client request
 * @author luoweiyi
 *
 */
public class ServerThreadPool {
	
	/**
	 * create fixed core thread
	 * @param nThreads
	 * @return
	 */
	public static ExecutorService fixedThreadPool(int nThreads){
		return new ThreadPoolExecutor(nThreads, nThreads, 0, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>(),new ServerThreadFactory());
	}

}
