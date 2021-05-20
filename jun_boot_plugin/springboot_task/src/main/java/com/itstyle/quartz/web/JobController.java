package com.itstyle.quartz.web;


import com.itstyle.quartz.entity.QuartzEntity;
import com.itstyle.quartz.entity.Result;
import com.itstyle.quartz.service.IJobService;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

	private final static Logger LOGGER = LoggerFactory.getLogger(JobController.class);

	@Autowired
    private Scheduler scheduler;
    @Autowired
    private IJobService jobService;
    
	@PostMapping("/add")
	public Result save(QuartzEntity quartz){
		LOGGER.info("新增任务");
        try {
            jobService.save(quartz);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
	}
	@PostMapping("/list")
	public Result list(QuartzEntity quartz,Integer pageNo,Integer pageSize) throws SchedulerException {
		LOGGER.info("任务列表");
        return jobService.listQuartzEntity(quartz, pageNo, pageSize);
	}
	@PostMapping("/trigger")
	public  Result trigger(QuartzEntity quartz,HttpServletResponse response) {
		LOGGER.info("触发任务");
		try {
		     JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
		     scheduler.triggerJob(key);
		} catch (SchedulerException e) {
			 e.printStackTrace();
			 return Result.error();
		}
		return Result.ok();
	}
	@PostMapping("/pause")
	public  Result pause(QuartzEntity quartz,HttpServletResponse response) {
		LOGGER.info("停止任务");
		try {
		     JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
		     scheduler.pauseJob(key);
		} catch (SchedulerException e) {
			 e.printStackTrace();
			 return Result.error();
		}
		return Result.ok();
	}
	@PostMapping("/resume")
	public  Result resume(QuartzEntity quartz,HttpServletResponse response) {
		LOGGER.info("恢复任务");
		try {
		     JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
		     scheduler.resumeJob(key);
		} catch (SchedulerException e) {
			 e.printStackTrace();
			 return Result.error();
		}
		return Result.ok();
	}
	@PostMapping("/remove")
	public  Result remove(QuartzEntity quartz,HttpServletResponse response) {
		LOGGER.info("移除任务");
		try {  
            TriggerKey triggerKey = TriggerKey.triggerKey(quartz.getJobName(), quartz.getJobGroup());  
            // 停止触发器  
            scheduler.pauseTrigger(triggerKey);  
            // 移除触发器  
            scheduler.unscheduleJob(triggerKey);  
            // 删除任务  
            scheduler.deleteJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));  
            System.out.println("removeJob:"+JobKey.jobKey(quartz.getJobName()));  
        } catch (Exception e) {  
        	e.printStackTrace();
            return Result.error();
        }  
		return Result.ok();
	}
}
