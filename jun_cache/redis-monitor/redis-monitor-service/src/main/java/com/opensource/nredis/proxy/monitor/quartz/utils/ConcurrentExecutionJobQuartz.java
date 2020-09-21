/**
 * 
 */
package com.opensource.nredis.proxy.monitor.quartz.utils;

import java.util.Date;
import java.util.List;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.opensource.nredis.proxy.monitor.lock.DistributedLock;
import com.opensource.nredis.proxy.monitor.lock.RedisDistributedLock;
import com.opensource.nredis.proxy.monitor.model.AutoPageSystemQrtzTriggerInfo;
import com.opensource.nredis.proxy.monitor.model.AutoPageSystemQrtzTriggerLog;
import com.opensource.nredis.proxy.monitor.quartz.IFrameworkQuartz;
/**
 * @author liubing
 *
 */
public class ConcurrentExecutionJobQuartz extends QuartzJobBean{
	
	private static Logger logger = LoggerFactory.getLogger(ConcurrentExecutionJobQuartz.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		Date triggerTime=new Date();
		JobKey jobKey = context.getTrigger().getJobKey();
		Integer jobGroup=Integer.parseInt(jobKey.getGroup());
		String jobName=jobKey.getName();
		AutoPageSystemQrtzTriggerInfo autoPageSystemQrtzTriggerInfo=new AutoPageSystemQrtzTriggerInfo();
		autoPageSystemQrtzTriggerInfo.setJobName(jobName);
		autoPageSystemQrtzTriggerInfo.setJobGroup(jobGroup);
		try{
			List<AutoPageSystemQrtzTriggerInfo> autoPageSystemQrtzTriggerInfos=DynamicSchedulerUtil.autoPageSystemQrtzTriggerInfoService.queryEntityList(autoPageSystemQrtzTriggerInfo);
			if(autoPageSystemQrtzTriggerInfos!=null&&autoPageSystemQrtzTriggerInfos.size()>0){
				autoPageSystemQrtzTriggerInfo =autoPageSystemQrtzTriggerInfos.get(0);
				Date handlerTime=new Date();
				if(autoPageSystemQrtzTriggerInfo.getJobClass()==2){//串行
							StringBuilder stringBuilder=new StringBuilder();
							stringBuilder.append(jobGroup).append("_").append(jobName);
							DistributedLock distributedLock=new RedisDistributedLock(stringBuilder.toString(), 30);//5s超时时间
							if(distributedLock.tryLock()){
								try{							
										IFrameworkQuartz frameworkQuartz=(IFrameworkQuartz) SpringContextUtil.getBean(autoPageSystemQrtzTriggerInfo.getClassName());
										String result=frameworkQuartz.invoke(autoPageSystemQrtzTriggerInfo.getAlarmThreshold());						
										createAutoPageSystemQrtzTriggerLog(triggerTime, handlerTime, "成功", "调度成功", result,result, autoPageSystemQrtzTriggerInfo);
								}finally{
										distributedLock.unLock();//释放锁
								}							
							}
				}else{//并行
							IFrameworkQuartz frameworkQuartz=(IFrameworkQuartz) SpringContextUtil.getBean(autoPageSystemQrtzTriggerInfo.getClassName());
							String result=frameworkQuartz.invoke(autoPageSystemQrtzTriggerInfo.getAlarmThreshold());					
							createAutoPageSystemQrtzTriggerLog(triggerTime, handlerTime, "成功", "调度成功", result,result, autoPageSystemQrtzTriggerInfo);
				}
			}
				
		}catch(Exception e){
			logger.error("ConcurrentExecutionJobQuartz excute method error,the method is executeInternal,Error Message:"+e.getMessage(), e);
			createAutoPageSystemQrtzTriggerLog(triggerTime, triggerTime, "失败", "调度失败","执行失败",e.getMessage(), autoPageSystemQrtzTriggerInfo);
		}
	
	}
	
	private void createAutoPageSystemQrtzTriggerLog(Date triggerTime,Date handlerTime,String status,String handleStatus,String handleResult,String msg,AutoPageSystemQrtzTriggerInfo autoPageSystemQrtzTriggerInfo) {
		try{
			AutoPageSystemQrtzTriggerLog autoPageSystemQrtzTriggerLog=new AutoPageSystemQrtzTriggerLog();
			
			autoPageSystemQrtzTriggerLog.setHandleStatus(handleStatus);
			autoPageSystemQrtzTriggerLog.setHandleMsg(handleResult);
			autoPageSystemQrtzTriggerLog.setHandleTime(handlerTime);
			autoPageSystemQrtzTriggerLog.setJobClass(autoPageSystemQrtzTriggerInfo.getJobClass());
			autoPageSystemQrtzTriggerLog.setJobCron(autoPageSystemQrtzTriggerInfo.getJobCron());
			autoPageSystemQrtzTriggerLog.setJobData(" ");
			autoPageSystemQrtzTriggerLog.setJobDesc(autoPageSystemQrtzTriggerInfo.getJobDesc());
			autoPageSystemQrtzTriggerLog.setJobGroup(autoPageSystemQrtzTriggerInfo.getJobGroup());
			autoPageSystemQrtzTriggerLog.setJobName(autoPageSystemQrtzTriggerInfo.getJobName());
			autoPageSystemQrtzTriggerLog.setTriggerMsg(msg);
			autoPageSystemQrtzTriggerLog.setTriggerStatus(status);		
			autoPageSystemQrtzTriggerLog.setTriggerTime(triggerTime);
			DynamicSchedulerUtil.autoPageSystemQrtzTriggerLogService.create(autoPageSystemQrtzTriggerLog);
			
		}catch(Exception e){
			logger.error("createAutoPageSystemQrtzTriggerLog error", e);
		}
		
	}
}
