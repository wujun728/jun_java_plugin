package com.baijob.commonTools;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 任务调度类
 * @author luxiaolei@baijob.com
 */
public class TaskScheduler extends Timer{
	private static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);
	
	public TaskScheduler(boolean isDeamon) {
		//daemon进程
		super(true);
	}
	
	/**
	 * 实例化一个新对象
	 * @param isDaemon 是否守护进程
	 * @return 任务调度对象
	 */
	public static TaskScheduler newInstance(boolean isDaemon){
		return new TaskScheduler(isDaemon);
	}
	
	/**
	 * 开始任务计划
	 * @param timerTask	定时任务对象
	 * @param startTime 开始时间 格式是 HH:mm:ss
	 * @param period 任务运行间隔的时间，毫秒
	 */
	public void schedule(TimerTask timerTask, String startTime, long period){
		String  firstDate = DateUtil.formatDate(new Date()) + " " + startTime;
		super.scheduleAtFixedRate(timerTask, DateUtil.parseDateTime(firstDate), period);
		logger.info("任务将于 " + firstDate + " 启动，启动间隔时间为 " + period /1000+ " 秒");
	}
	
	/**
	 * 开始任务计划
	 * @param timerTask 定时任务对象
	 * @param delay 从启动起等待的时间，毫秒
	 * @param period 任务运行间隔的时间，毫秒
	 */
	public void schedule(TimerTask timerTask, long delay, long period) {
		super.scheduleAtFixedRate(timerTask, delay, period);
		logger.info("任务将于 " + delay / 1000 + " 秒后启动，启动间隔时间为 " + period / 1000 + " 秒");
	}
}
