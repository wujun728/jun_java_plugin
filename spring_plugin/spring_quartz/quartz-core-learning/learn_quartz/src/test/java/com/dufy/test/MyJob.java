package com.dufy.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyJob implements Job{
	
	private static Logger log = LoggerFactory.getLogger(MyJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("-----------------start sleep--------------------");
		log.info("date : " + sf.format(new Date()) + "-----------jobName---------" + jobDetail.getKey().getName());
		/*try {
			//Thread.sleep(1 * 10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		log.info("-----------------start end--------------------");
		
	}

}
