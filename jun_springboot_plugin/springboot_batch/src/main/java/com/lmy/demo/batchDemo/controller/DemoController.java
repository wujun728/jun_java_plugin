package com.lmy.demo.batchDemo.controller;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoController implements ApplicationContextAware {
	@Autowired(required = false)
	JobLauncher jobLauncher;

	private ApplicationContext applicationContext;

	public JobParameters jobParameters;

	// @Scheduled(cron = "0 0 8 * * ?") // 早上8点
	// 每分钟跑一次
	@Scheduled(cron = "0/10 * * * * ?")
	public void imp() throws Exception {
		System.out.println("11111111112");
		Job job = (Job) this.applicationContext.getBean("dataHandleJob");
		jobParameters = new JobParametersBuilder().addLong("time",
				System.currentTimeMillis()).toJobParameters();
		jobLauncher.run(job, jobParameters);
	}
	
	@Scheduled(cron = "0/2 * * * * ?")
	public void hello(){
		System.out.println("hello "+new Date());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}