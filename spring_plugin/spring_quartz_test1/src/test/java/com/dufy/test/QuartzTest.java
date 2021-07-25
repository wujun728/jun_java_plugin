package com.dufy.test;


import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QuartzTest {
	static Logger log = LoggerFactory.getLogger(QuartzTest.class);
	
	public static void main(String[] args) {
		testStart();
	}
	
	public static SchedulerFactory sf = new StdSchedulerFactory();
	
	@Test
	public void testAdd() {
	 try {
	        log.info("------- Initializing ----------------------");
	        //初始化调度器工厂
	        //初始化调度器
	        Scheduler sched  = sf.getScheduler();
			
	        log.info("------- Initialization Complete -----------");
	
	        // 获取当前时间
	        Date runTime = new Date();
	
	        log.info("------- Scheduling Job  -------------------");
	        for (int i = 1; i <= 1; i++) {
	        	// 定义job
		        // 在quartz中，有组的概念，组+job名称 唯一的
		        JobDetail job = newJob(MyJob.class).withIdentity("dufy_job" + i, "dufy_group").build();
		        job.getJobDataMap().put("jobName", "dufy_job" + i);
		        //SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(10);
				// 定义触发器，在下一分钟启动   .withSchedule(schedBuilder).startAt(runTime).build();
		        String cron ="0/" +((i*2)+1)+ " * * * * ?";
		        Trigger trigger = newTrigger().withIdentity("dufy_trigger" + i, "dufy_trigger_group")
		        		.withSchedule(cronSchedule(cron)).build();
		        // 将job注册到调度器
				sched.scheduleJob(job, trigger);
				
				log.info(job.getKey() + " will run at: " + runTime);
			}
	       
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      

     
	}
	/**
	 * 启动调度器
	 */
	public static void testStart(){
		try {
			
	        //初始化调度器
	        Scheduler sched  = sf.getScheduler();
	        
			 // 启动调度器
			sched.start();
			log.info("------- Started Sc11heduler -----------------");
			
		} catch (SchedulerException e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 运行一次 触发器
	 */
	@Test
	public void testRunOnceTrigger(){
		
        try {
        	//初始化调度器
			Scheduler sched = sf.getScheduler();
			//triggerName  triggerGroup
			TriggerKey tk = new TriggerKey("dufy_trigger1", "dufy_trigger_group");
			Trigger t = sched.getTrigger(tk);
			System.out.println("---" + t.getJobKey().getName());
			sched.triggerJob(t.getJobKey());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	
	/**
	 * 测试暂停所有的触发器
	 */
	@Test
	public void testPauseAllTrigger(){
		
        try {
        	//初始化调度器
			Scheduler sched = sf.getScheduler();
			sched.pauseAll();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	/**
	 * 测试恢复所有的触发器
	 */
	@Test
	public void testResumeAllTrigger(){
		
        try {
        	//初始化调度器
			Scheduler sched = sf.getScheduler();
			sched.resumeAll();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	/**
	 * 关闭 调度器
	 */
	@Test
	public void testShutDown(){
		try {
			//初始化调度器工厂
	        // SchedulerFactory sf = new StdSchedulerFactory();
	        //初始化调度器
	        Scheduler sched = sf.getScheduler();
			   // 等待65秒
		        log.info("------- Waiting 65 seconds... -------------");
		        try {
		            // wait 65 seconds to show job
		            Thread.sleep(5L * 1000L);
		            // executing...
		        } catch (Exception e) {
		            //
		        }
	
	        // 关闭调度器
	        log.info("------- Shutting Down ---------------------");
	        sched.shutdown(true);
	        log.info("------- Shutdown Complete -----------------");
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
}
