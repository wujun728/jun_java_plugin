package net.oschina.quartzutils.job;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.oschina.quartzutils.job.QuartzJobFactory;
import net.oschina.quartzutils.job.QuartzJobFactoryDisallowConcurrentExecution;
import net.oschina.quartzutils.jobean.ScheduleJob;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * 使用quartz 2.2 进行任务调度
 *
 * @author zdc
 *
 */
public class QuartzUtils {
	final static Logger logger = Logger.getLogger(QuartzUtils.class);
	private static ApplicationContext ac;

	Scheduler scheduler;
	String name;

	/**
	 * 
	 * @throws SchedulerException
	 */
	public QuartzUtils() throws SchedulerException {
		// Log log = Log.getLoger();
		ac = new ClassPathXmlApplicationContext(
				"file:config/applicationContext.xml");
		scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		name = scheduler.getSchedulerName();
		logger.debug("[调度名称]" + name);
	}

	/**
	 * 
	 * @param name
	 * @throws SchedulerException
	 */
	public QuartzUtils(String name) throws SchedulerException {
		ac = new ClassPathXmlApplicationContext(
				"file:config/applicationContext.xml");
		scheduler = (Scheduler) ac.getBean(name);
		name = scheduler.getSchedulerName();
		logger.debug("[调度名称]" + name);
	}

	/**
	 * 关闭调度
	 * 
	 * @throws SchedulerException
	 */
	public void shutdown() throws SchedulerException {
		Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		logger.debug(scheduler
				+ ".......................................................................................shutdown");
		scheduler.shutdown();

	}

	/**
	 * 暂停调度
	 * 
	 * @throws SchedulerException
	 */
	public void standby() throws SchedulerException {
		Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		logger.debug(scheduler
				+ ".......................................................................................standby");
		scheduler.standby();
		

	}

	/**
	 * 获取正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		// Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		List<JobExecutionContext> executingJobs = scheduler
				.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(
				executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler
					.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 获取所有任务
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {

		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler
					.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler
						.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 添加任务
	 * 
	 * @param job
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null
				|| !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return;
		}

		// Scheduler scheduler = schedulerFactoryBean.getScheduler();
		logger.debug(scheduler
				+ ".......................................................................................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),
				job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class<? extends Job> clazz = ScheduleJob.CONCURRENT_IS.equals(job
					.getIsConcurrent()) ? QuartzJobFactory.class
					: QuartzJobFactoryDisallowConcurrentExecution.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz)
					.withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			CronScheduleBuilder scheduleBuilder = null;
			if (job.getMisfireStatus().equals(
					ScheduleJob.MISFIRE_STATUS_DO_NOTHING)) {
				scheduleBuilder = CronScheduleBuilder.cronSchedule(
						job.getCronExpression())
						.withMisfireHandlingInstructionDoNothing();
			} else if (job.getMisfireStatus().equals(
					ScheduleJob.MISFIRE_STATUS_FIREANDPROCEED)) {
				scheduleBuilder = CronScheduleBuilder.cronSchedule(
						job.getCronExpression())
						.withMisfireHandlingInstructionFireAndProceed();
			} else if (job.getMisfireStatus().equals(
					ScheduleJob.MISFIRE_STATUS_IGNORE_MISFIRES)) {
				scheduleBuilder = CronScheduleBuilder.cronSchedule(
						job.getCronExpression())
						.withMisfireHandlingInstructionIgnoreMisfires();
			} else {
				scheduleBuilder = CronScheduleBuilder.cronSchedule(job
						.getCronExpression());
			}
			// trigger = TriggerBuilder.newTrigger()
			// .withIdentity(job.getJobName(), job.getJobGroup())
			// .withSchedule(scheduleBuilder).
			if (job.getStartTime() != null & job.getEndTime() != null) {
				trigger = TriggerBuilder.newTrigger()
						.withIdentity(job.getJobName(), job.getJobGroup())
						.withSchedule(scheduleBuilder)
						.startAt(job.getStartTime()).endAt(job.getEndTime())
						.build();
			} else if (job.getStartTime() != null & job.getEndTime() == null) {
				trigger = TriggerBuilder.newTrigger()
						.withIdentity(job.getJobName(), job.getJobGroup())
						.withSchedule(scheduleBuilder)
						.startAt(job.getStartTime()).build();

			} else if (job.getStartTime() == null & job.getEndTime() != null) {
				trigger = TriggerBuilder.newTrigger()
						.withIdentity(job.getJobName(), job.getJobGroup())
						.withSchedule(scheduleBuilder).endAt(job.getEndTime())
						.build();

			} else {
				trigger = TriggerBuilder.newTrigger()
						.withIdentity(job.getJobName(), job.getJobGroup())
						.withSchedule(scheduleBuilder).build();
			}
			scheduler.scheduleJob(jobDetail, trigger);

		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		// Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
				scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		// Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
				scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);

	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		// Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
				scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		// Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
				scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	public void addNewDeviceJob(JobDetail detail) {
		// Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		SimpleTrigger trigger1 = newTrigger()
				.withIdentity("trigger1", "group1")
				.startAt(new Date())
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(10)
								.withRepeatCount(0)).build();

		detail.getJobDataMap().put("xxxx", "######  绿   #####");

		Date scheduleTime1 = null;
		try {
			scheduleTime1 = scheduler.scheduleJob(detail, trigger1);
		} catch (SchedulerException e) {

			e.printStackTrace();
		}
		System.out.println(detail.getKey().getName() + " 将在 : " + scheduleTime1
				+ " 执行, 并重复 : " + trigger1.getRepeatCount() + " 次, 每次间隔   "
				+ trigger1.getRepeatInterval() / 1000 + " 秒");

	}

	/**
	 * 更新job执行时间
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJob scheduleJob)
			throws SchedulerException {
		// Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(),
				scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = null;
		if (scheduleJob.getMisfireStatus().equals(
				ScheduleJob.MISFIRE_STATUS_DO_NOTHING)) {
			scheduleBuilder = CronScheduleBuilder.cronSchedule(
					scheduleJob.getCronExpression())
					.withMisfireHandlingInstructionDoNothing();
		} else if (scheduleJob.getMisfireStatus().equals(
				ScheduleJob.MISFIRE_STATUS_FIREANDPROCEED)) {
			scheduleBuilder = CronScheduleBuilder.cronSchedule(
					scheduleJob.getCronExpression())
					.withMisfireHandlingInstructionFireAndProceed();
		} else if (scheduleJob.getMisfireStatus().equals(
				ScheduleJob.MISFIRE_STATUS_IGNORE_MISFIRES)) {
			scheduleBuilder = CronScheduleBuilder.cronSchedule(
					scheduleJob.getCronExpression())
					.withMisfireHandlingInstructionIgnoreMisfires();
		} else {
			scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob
					.getCronExpression());
		}

		if (scheduleJob.getStartTime() != null
				& scheduleJob.getEndTime() != null) {
			trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(scheduleJob.getJobName(),
							scheduleJob.getJobGroup())
					.withSchedule(scheduleBuilder)
					.startAt(scheduleJob.getStartTime())
					.endAt(scheduleJob.getEndTime()).build();
		} else if (scheduleJob.getStartTime() != null
				& scheduleJob.getEndTime() == null) {
			trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(scheduleJob.getJobName(),
							scheduleJob.getJobGroup())
					.withSchedule(scheduleBuilder)
					.startAt(scheduleJob.getStartTime()).build();

		} else if (scheduleJob.getStartTime() == null
				& scheduleJob.getEndTime() != null) {
			trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(scheduleJob.getJobName(),
							scheduleJob.getJobGroup())
					.withSchedule(scheduleBuilder)
					.endAt(scheduleJob.getEndTime()).build();

		} else {
			trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(scheduleJob.getJobName(),
							scheduleJob.getJobGroup())
					.withSchedule(scheduleBuilder).build();
		}

		scheduler.rescheduleJob(triggerKey, trigger);
	}

	public static void main(String[] args) throws ConfigurationException,
			SchedulerException, InterruptedException {
		QuartzUtils collectMain = new QuartzUtils();
		ScheduleJob job = new ScheduleJob();
		job.setBeanClass("com.gloryscience.config.main.TestJob");
		job.setCronExpression("0/20 * * * * ?");// Cron表达式
		job.setJobStatus(ScheduleJob.STATUS_RUNNING);
		job.setIsConcurrent(ScheduleJob.CONCURRENT_NOT);// 是否允许并行运行
		job.setJobName("TestJob");// job名称
		job.setJobGroup("test");
		job.setMethodName("test");
		job.setErrorStatus(ScheduleJob.ERROR_NO_STOP);// 任务出现错误后是否停止
		job.setMisfireStatus(ScheduleJob.MISFIRE_STATUS_DO_NOTHING);// 调度策略详见注释
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		// String str="20110823";
		// job.getStartTime()
		Date dt = new Date();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		// rightNow.add(Calendar.YEAR,-1);//日期减1年
		// rightNow.add(Calendar.MONTH,3);//日期加3个月
		rightNow.add(Calendar.SECOND, 20);// 日期加10天
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		String reStr1 = sdf.format(dt);
		System.out.println(reStr1);
		System.out.println(reStr);

		job.setStartTime(dt1);// job开始时间
		collectMain.addJob(job);

		Thread.sleep(2000000);// 等待20秒
		collectMain.shutdown();
		// try {
		// PropertiesConfiguration config = new PropertiesConfiguration(
		// "config/config.properties");
		// System.out.println(co

		// config.getString("where"));
		// } catch (ConfigurationException e) {
		//
		// e.printStackTrace();
		// }

	}

}