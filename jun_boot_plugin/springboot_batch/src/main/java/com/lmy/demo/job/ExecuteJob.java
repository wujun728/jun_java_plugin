package com.lmy.demo.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ExecuteJob implements Job {
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String name = context.getJobDetail().getKey().getName();
		System.out.println("定时调度启动成功");
	}
}