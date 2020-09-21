package org.coody.framework.task.container;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SysTimerContainer {
	public static final ScheduledExecutorService SYSTEMTIMER = new ScheduledThreadPoolExecutor(200);
	public static void execute(TimerTask task,Integer delayed){
		try {
			SYSTEMTIMER.schedule(task, delayed*1000,TimeUnit.MILLISECONDS);
		} catch (Exception e) {
		}
		
	}
}
