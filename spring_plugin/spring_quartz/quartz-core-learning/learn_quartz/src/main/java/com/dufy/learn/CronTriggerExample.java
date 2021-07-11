/* 
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package com.dufy.learn;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * This Example will demonstrate how to start and shutdown the Quartz scheduler and how to schedule
 * a job to run in Quartz.
 * 
 * @author Bill Kratzer
 */
public class CronTriggerExample {
	 Logger log = LoggerFactory.getLogger(CronTriggerExample.class);

    public void run() throws Exception {
       
        log.info("------- Initializing ----------------------");

        // 定义调度器
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete -----------");

        // 获取当前时间的下一分钟
        Date runTime = evenMinuteDate(new Date());

        log.info("------- Scheduling Job  -------------------");

        // 定义job
        JobDetail job = newJob(HelloCronJob.class).withIdentity("job1", "group1").build();
        
        // 定义触发器，每2秒执行一次
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0/2 * * * * ?")).build();

        // 将job注册到调度器
        sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + runTime);

        // 启动调度器
        //sched.start();

        log.info("------- Started Scheduler -----------------");

        // 等待1分钟
        log.info("------- Waiting 60 seconds... -------------");
        try {
            Thread.sleep(60L * 1000L);
        } catch (Exception e) {
            //
        }

        // 关闭调度器
        log.info("------- Shutting Down ---------------------");
        //sched.shutdown(true);
        log.info("------- Shutdown Complete -----------------");
    }

    public static void main(String[] args) throws Exception {

        CronTriggerExample example = new CronTriggerExample();
        //example.run();
        example.start();
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
	        
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    /**
	 * 从数据库中找到已经存在的job，并重新开户调度
	 */
	public static void resumeJob() {
		try {

			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			// ①获取调度器中所有的触发器组
			List<String> JobGroups = scheduler.getJobGroupNames();
			
			// ②重新恢复在dufy_group1组中，名为dufy_trigger1触发器的运行
			for (int i = 0; i < JobGroups.size(); i++) {
				List<String> triggerGroups = scheduler.getTriggerGroupNames();
				for (int j = 0; j < triggerGroups.size(); j++) {
					//SELECT * FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?
					Trigger tg = scheduler.getTrigger(new TriggerKey(triggerGroups
							.get(j), JobGroups.get(i)));
					// ②-1:根据名称判断
					if (tg instanceof SimpleTrigger
							&& tg.getDescription().equals("group1.trigger1")) {
						// ②-1:恢复运行
						scheduler.resumeJob(new JobKey(triggerGroups.get(j),
								JobGroups.get(i)));
					}
				}

			}
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
