package net.oschina.quartzutils.job;

import java.lang.reflect.InvocationTargetException;

import net.oschina.quartzutils.jobean.ScheduleJob;
import net.oschina.quartzutils.utils.TaskUtils;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;




/**
 * 
 * @author zdc
 *
 */

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	public final Logger log = Logger.getLogger(this.getClass()); 
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		log.info("调用不允许并行job");
		try {
			TaskUtils.invokMethod(scheduleJob);
		} catch (IllegalArgumentException e) {
			JobExecutionException e2 = new JobExecutionException(new Exception());  
			throw e2;
		} catch (IllegalAccessException e) {
			JobExecutionException e2 = new JobExecutionException(new Exception());  
			throw e2;
		} catch (InvocationTargetException e) {
			log.debug("异常");
			JobExecutionException e2 = new JobExecutionException(e);
			e2.setUnscheduleAllTriggers(true);
			e.printStackTrace();
			if(ScheduleJob.ERROE_STOP.equals(scheduleJob.getErrorStatus()))
			{
				throw e2;
			}
			else
			{
				e2.printStackTrace();
				log.debug(e2);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

}
