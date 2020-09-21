package com.opensource.nredis.proxy.monitor.quartz.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.opensource.nredis.proxy.monitor.enums.JobClassEnums;
import com.opensource.nredis.proxy.monitor.model.AutoPageSystemQrtzTriggerInfo;
import com.opensource.nredis.proxy.monitor.service.IAutoPageSystemQrtzTriggerInfoService;
import com.opensource.nredis.proxy.monitor.service.IAutoPageSystemQrtzTriggerLogService;


/**
 * 
 * @author liubing
 *
 */
public final class DynamicSchedulerUtil implements ApplicationContextAware, InitializingBean {
	
    private static final Logger logger = LoggerFactory.getLogger(DynamicSchedulerUtil.class);
    
    // Scheduler
    private static Scheduler scheduler;
    public static void setScheduler(Scheduler scheduler) {
		DynamicSchedulerUtil.scheduler = scheduler;
	}

    public static IAutoPageSystemQrtzTriggerInfoService autoPageSystemQrtzTriggerInfoService;
    
    public static IAutoPageSystemQrtzTriggerLogService autoPageSystemQrtzTriggerLogService;
    
    @Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		DynamicSchedulerUtil.autoPageSystemQrtzTriggerInfoService = applicationContext.getBean(IAutoPageSystemQrtzTriggerInfoService.class);
		DynamicSchedulerUtil.autoPageSystemQrtzTriggerLogService = applicationContext.getBean(IAutoPageSystemQrtzTriggerLogService.class);
    }
    
	@Override
    public void afterPropertiesSet() throws Exception {
       
    }
	
	// getJobKeys
	@Deprecated
	public static List<Map<String, Object>> getJobList(){
		List<Map<String, Object>> jobList = new ArrayList<Map<String,Object>>();
		
		try {
			if (scheduler.getJobGroupNames()==null || scheduler.getJobGroupNames().size()==0) {
				return null;
			}
			String groupName = scheduler.getJobGroupNames().get(0);
			Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
			if (jobKeys!=null && jobKeys.size()>0) {
				for (JobKey jobKey : jobKeys) {
			        TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), Scheduler.DEFAULT_GROUP);
			        Trigger trigger = scheduler.getTrigger(triggerKey);
			        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			        TriggerState triggerState = scheduler.getTriggerState(triggerKey);
			        Map<String, Object> jobMap = new HashMap<String, Object>();
			        jobMap.put("TriggerKey", triggerKey);
			        jobMap.put("Trigger", trigger);
			        jobMap.put("JobDetail", jobDetail);
			        jobMap.put("TriggerState", triggerState);
			        jobList.add(jobMap);
				}
			}
			
		} catch (SchedulerException e) {
			logger.error("getJobList error ", e);
			//e.printStackTrace();
			return null;
		}
		return jobList;
	}
	
	// fill job info
	public static void fillJobInfo(AutoPageSystemQrtzTriggerInfo jobInfo) {
		// TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJobName(), String.valueOf(jobInfo.getJobGroup()));
        JobKey jobKey = new JobKey(jobInfo.getJobName(), String.valueOf(jobInfo.getJobGroup()));
        try {
			Trigger trigger = scheduler.getTrigger(triggerKey);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			TriggerState triggerState = scheduler.getTriggerState(triggerKey);
			
			// parse params
			if (trigger!=null && trigger instanceof CronTriggerImpl) {
				String cronExpression = ((CronTriggerImpl) trigger).getCronExpression();
				jobInfo.setJobCron(cronExpression);
			}
			if (jobDetail!=null) {
				jobInfo.setJobClassName(JobClassEnums.getMessage(jobInfo.getJobClass()));
			}
			if (triggerState!=null) {
				jobInfo.setJobStatus(triggerState.name());
			}
			
		} catch (SchedulerException e) {
			logger.error("fillJobInfo error ", e);
		}
	}
	
	// check if exists
	public static boolean checkExists(String jobName, String jobGroup) throws SchedulerException{
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		return scheduler.checkExists(triggerKey);
	}

	// addJob 新增
    @SuppressWarnings("unchecked")
	public static boolean addJob(AutoPageSystemQrtzTriggerInfo jobInfo) throws SchedulerException {
    	// TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJobName(), String.valueOf(jobInfo.getJobGroup()));
        JobKey jobKey = new JobKey(jobInfo.getJobName(), String.valueOf(jobInfo.getJobGroup()));
        
        // TriggerKey valid if_exists
        if (checkExists(jobInfo.getJobName(), String.valueOf(jobInfo.getJobGroup()))) {
            return false;
        }
        
        // CronTrigger : TriggerKey + cronExpression	// withMisfireHandlingInstructionDoNothing 忽略掉调度终止过程中忽略的调度
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getJobCron()).withMisfireHandlingInstructionDoNothing();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

        // JobDetail : jobClass
		Class<? extends Job> jobClass_ = null;
		try {
			jobClass_=(Class<? extends Job>)Class.forName(JobClassEnums.getClass(jobInfo.getJobClass()));
		} catch (ClassNotFoundException e) {
			logger.error("无法加载类,类名:"+JobClassEnums.getClass(jobInfo.getJobClass()), e);
		}
        
		JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(jobKey).build();
        
        scheduler.scheduleJob(jobDetail, cronTrigger);
        return true;
    }
    
    // reschedule
	public static boolean rescheduleJob(AutoPageSystemQrtzTriggerInfo jobInfo) throws SchedulerException {
    	
    	// TriggerKey valid if_exists
        if (!checkExists(jobInfo.getJobName(), String.valueOf(jobInfo.getJobGroup()))) {
            return false;
        }
        
        // TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJobName(), String.valueOf(jobInfo.getJobGroup()));
        JobKey jobKey = new JobKey(jobInfo.getJobName(), String.valueOf(jobInfo.getJobGroup()));
        
        // CronTrigger : TriggerKey + cronExpression
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getJobCron()).withMisfireHandlingInstructionDoNothing();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
        
        //scheduler.rescheduleJob(triggerKey, cronTrigger);
        
        // JobDetail-JobDataMap fresh
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

    	// Trigger fresh
    	HashSet<Trigger> triggerSet = new HashSet<Trigger>();
    	triggerSet.add(cronTrigger);
        
        scheduler.scheduleJob(jobDetail, triggerSet, true);
        return true;
    }
    
    // unscheduleJob
    public static boolean removeJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        //boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.unscheduleJob(triggerKey);
			JobKey jobKey = new JobKey(jobName, jobGroup);
			scheduler.deleteJob(jobKey);
        }
        return true;
    }

    // Pause
    public static boolean pauseJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.pauseTrigger(triggerKey);
            result = true;
        }
        return result;
    }
    
    // resume
    public static boolean resumeJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.resumeTrigger(triggerKey);
            result = true;
        }
        return result;
    }
    
    // run
    public static boolean triggerJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	JobKey jobKey = new JobKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.triggerJob(jobKey);
            result = true;
        }
        return result;
    }


}