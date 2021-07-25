package com.dufy.learn;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.JobStoreSupport;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.spi.JobStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * This  is a simpleTrigger test!!!
 * @author dufy
 */
public class SimpleTriggerExample {
	static Logger log = LoggerFactory.getLogger(SimpleTriggerExample.class);
	
	private static final String job_name = "dufy_job2";
	private static final String job_group = "dufy_job_group2";
	
	private static final String trigger_name = "dufy_trigger2";
	private static final String trigger_group = "dufy_trigger_group2";

	public static void main(String[] args) throws Exception {
        SimpleTriggerExample example = new SimpleTriggerExample();
        
        //注册
        example.register(job_name, job_group, trigger_name, trigger_group,10*1000L);
        //启动
        example.start();
        //恢复
        //example.resumeJob("dufy_job1", "dufy_group1");
	}
	
	/**
	 * 注册触发器
	 * @param jobName job名称
	 * @param jobGroup job组名
	 * @param triggerName trigger名称
	 * @param triggerGroup trigger组名
	 * @param afterTime 当前时间 之后多少秒执行( 1*1000L : 1s)
	 * @throws Exception
	 */
    public void register(String jobName,String jobGroup,String triggerName,String triggerGroup,long afterTime) throws Exception {

        log.info("------- Initializing ----------------------");

        //初始化调度器工厂
        SchedulerFactory sf = new StdSchedulerFactory();
        //初始化调度器
        Scheduler sched = sf.getScheduler();
        

        log.info("------- Initialization Complete -----------");

        // 获取当前时间的afterTime s之后
        long time=  System.currentTimeMillis() + afterTime;
        Date runTime = new Date(time);

        log.info("------- Scheduling Job  start-------------------");

        // 定义job
        // 在quartz中，有组的概念，组+job名称 唯一的
        JobDetail job = newJob(HelloSimpleJob.class).withIdentity(jobName, jobGroup).build();

        //是否重复
        // SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(1);
		// 定义触发器，在下一分钟启动
        Trigger trigger = newTrigger().withIdentity(triggerName, triggerGroup).withSchedule(SimpleScheduleBuilder.simpleSchedule()).startAt(runTime).build();

        // 将job注册到调度器
        sched.scheduleJob(job, trigger);
        
        log.info("------- Scheduling Job  complete-------------------");
        
        log.info(job.getKey() + " will run at: " + runTime);
        
        
    }

    /**
     * 启动 Scheduler
     */
    public void start(){
		try {
			log.info("------- Started Scheduler -----------------");
			//初始化调度器工厂
	        SchedulerFactory sf = new StdSchedulerFactory();
	        //初始化调度器
	        Scheduler sched = sf.getScheduler();
	        
	        sched.start();
	        
	        /*
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
		     */
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
 
    /**
     * 从数据库中找到已经存在的job，并重新开户调度
     * @param jobName 触发器对应得jobName
     * @param jobGroup  触发器对应得jobGroup
     */
	public void resumeJob(String jobName ,String jobGroup) {
		try {

			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			
			JobKey jobKey = new JobKey(jobName,jobGroup);
			//SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			
			// 重新恢复触发器的运行
			if(triggers.size() > 0){
				for (Trigger tg : triggers) {
					// 根据类型判断
					if ((tg instanceof CronTrigger) || (tg instanceof SimpleTrigger)) {
						// 恢复job运行
						scheduler.resumeJob(jobKey);
					}
				}
				scheduler.start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public void acquireNextTriggers(){
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobStoreSupport js = new JobStoreTX();
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
