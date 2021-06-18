package com.jun.plugin.spring.job.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 此注解相当于单例模式，实现共享数据统一
 */
@PersistJobDataAfterExecution
public class HelloJob implements Job {
    public HelloJob() {
        System.out.println("welcome to hello job");
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //输出当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        final String format = dateFormat.format(date);
        System.out.println(format);
        System.out.println(jobExecutionContext.getJobDetail());
        System.out.println(jobExecutionContext.getJobDetail().getJobDataMap().getString("message"));
        System.out.println(jobExecutionContext.getTrigger().getJobDataMap().getString("message"));
//        jobExecutionContext.getTrigger().getKey();
    }
}
