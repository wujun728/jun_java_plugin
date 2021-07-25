package com.dufy.spring.quartz.chapter01.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spring 整合 Quartz 的  Hello World!
 * @author dufy
 * @date 2017.02.26
 *
 */
public class HelloWorldJob implements Job{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("This is a first spring combine quartz !");
		log.info("Welcome to Spring_Quartz World!"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
		log.info("Let's begin ! \n \n");
	}

}
