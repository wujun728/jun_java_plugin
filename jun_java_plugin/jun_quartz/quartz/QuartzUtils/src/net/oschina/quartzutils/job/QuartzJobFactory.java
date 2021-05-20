package net.oschina.quartzutils.job;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.oschina.quartzutils.jobean.ScheduleJob;
import net.oschina.quartzutils.utils.TaskUtils;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 
 * @author 张大川
 *
 */
@PersistJobDataAfterExecution
public class QuartzJobFactory implements Job {
	public final Logger log = Logger.getLogger(this.getClass());
	public static final String NUM_EXECUTIONS = "NumExecutions";
	public static final String EXECUTION_DELAY = "ExecutionDelay";

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap()
				.get("scheduleJob");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String jobRunTime = dateFormat.format(Calendar.getInstance().getTime());

		log.debug("---" + context.getJobDetail().getKey().getName() + " 在  : ["
				+ jobRunTime + "] 执行了!!");

		log.debug("调用普通job");
		try {
			TaskUtils.invokMethod(scheduleJob);
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			log.debug("异常");
			JobExecutionException e2 = new JobExecutionException(e);
			e2.setUnscheduleAllTriggers(true);
			e.printStackTrace();
			if (ScheduleJob.ERROE_STOP.equals(scheduleJob.getErrorStatus())) {
				throw e2;
			} else {
				e2.printStackTrace();
				log.debug(e2);
			}

		} catch (Exception e) {
			log.debug("异常");
			e.printStackTrace();
		}

	}

}