package org.ssm.dufy.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssm.dufy.service.QuartzService;

@Service("quartzService")
public class QuartzServiceImpl implements QuartzService{
	
	@Autowired
	private Scheduler quartzScheduler;
	
	@Override
	public void addJob(String jobName, String jobGroupName, String triggerName,
			String triggerGroupName, Class cls, String cron) {
		try {
			// 获取调度器
			Scheduler sched = quartzScheduler;
			// 创建一项作业
			JobDetail job = JobBuilder.newJob(cls)
					.withIdentity(jobName, jobGroupName).build();
			// 创建一个触发器
			CronTrigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(triggerName, triggerGroupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(cron))
					.build();
			// 告诉调度器使用该触发器来安排作业
			sched.scheduleJob(job, trigger);
			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改定时器任务信息
	 */
	@Override
	public boolean modifyJobTime(String oldjobName, String oldjobGroup, String oldtriggerName, String oldtriggerGroup, String jobName, String jobGroup,
			String triggerName, String triggerGroup, String cron) {
		try {
			Scheduler sched = quartzScheduler;
			CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
					.triggerKey(oldtriggerName, oldtriggerGroup));
			if (trigger == null) {
				return false;
			}

			JobKey jobKey = JobKey.jobKey(oldjobName, oldjobGroup);
			TriggerKey triggerKey = TriggerKey.triggerKey(oldtriggerName,
					oldtriggerGroup);

			JobDetail job = sched.getJobDetail(jobKey);
			Class jobClass = job.getJobClass();
			// 停止触发器
			sched.pauseTrigger(triggerKey);
			// 移除触发器
			sched.unscheduleJob(triggerKey);
			// 删除任务
			sched.deleteJob(jobKey);
			
			addJob(jobName, jobGroup, triggerName, triggerGroup, jobClass,
					cron);
			
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void modifyJobTime(String triggerName, String triggerGroupName,
			String time) {
		try {
			Scheduler sched = quartzScheduler;
			CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
					.triggerKey(triggerName, triggerGroupName));
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.getTriggerBuilder()
						.withSchedule(CronScheduleBuilder.cronSchedule(time))
						.build();
				// 重启触发器
				sched.resumeTrigger(TriggerKey.triggerKey(triggerName,
						triggerGroupName));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = quartzScheduler;
			// 停止触发器
			sched.pauseTrigger(TriggerKey.triggerKey(triggerName,
					triggerGroupName));
			// 移除触发器
			sched.unscheduleJob(TriggerKey.triggerKey(triggerName,
					triggerGroupName));
			// 删除任务
			sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startSchedule() {
		try {
			Scheduler sched = quartzScheduler;
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutdownSchedule() {
		try {
			Scheduler sched = quartzScheduler;
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void pauseJob(String jobName, String jobGroupName) {
		try {
			quartzScheduler.pauseJob( JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public void resumeJob(String jobName, String jobGroupName) {
		try {
			quartzScheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	
}
