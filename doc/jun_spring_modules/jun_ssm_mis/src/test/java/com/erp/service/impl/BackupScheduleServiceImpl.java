package com.erp.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.erp.service.IBackupScheduleService;
import com.erp.service.IDbBackUpService;
import com.jun.plugin.utils.Constants;

@Service("backupScheduleService")
public class BackupScheduleServiceImpl implements IBackupScheduleService {
	/*
	 * (非 Javadoc) <p>Title: execute</p> <p>Description:备份调度执行方法 </p>
	 * 
	 * @param jobCtx
	 * 
	 * @throws JobExecutionException
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
		String fineName = Constants.dbBackUp();
		String sqlName = Constants.BASE_PATH + "attachment" + File.separator + "dbBackUp" + File.separator + fineName;
		System.out.println(jobCtx.getTrigger().getCalendarName() + " triggered. time is:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		SpringWiredBean sdf = SpringWiredBean.getInstance();
		IDbBackUpService sdsdf = (IDbBackUpService) sdf.getBeanById("dbBackUpService");
		sdsdf.addLog(sqlName, fineName, true);
	}
}
