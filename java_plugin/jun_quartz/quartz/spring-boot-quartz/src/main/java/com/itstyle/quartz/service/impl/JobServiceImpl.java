package com.itstyle.quartz.service.impl;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.itstyle.quartz.dynamicquery.DynamicQuery;
import com.itstyle.quartz.entity.QuartzEntity;
import com.itstyle.quartz.service.IJobService;
@Service("jobService")
public class JobServiceImpl implements IJobService {

	@Autowired
	private DynamicQuery dynamicQuery;
    @Autowired @Qualifier("Scheduler")
    private Scheduler scheduler;

	@Override
	public List<QuartzEntity> listQuartzEntity(QuartzEntity quartz,
			Integer pageNo, Integer pageSize) throws SchedulerException {
		StringBuffer nativeSql = new StringBuffer();
		nativeSql.append("SELECT job.JOB_NAME as jobName,job.JOB_GROUP as jobGroup,job.DESCRIPTION as description,job.JOB_CLASS_NAME as jobClassName,");
		nativeSql.append("cron.CRON_EXPRESSION as cronExpression,tri.TRIGGER_NAME as triggerName,tri.TRIGGER_STATE as triggerState,");
		nativeSql.append("job.JOB_NAME as oldJobName,job.JOB_GROUP as oldJobGroup ");
		nativeSql.append("FROM qrtz_job_details AS job ");
        nativeSql.append("LEFT JOIN qrtz_triggers AS tri ON job.JOB_NAME = tri.JOB_NAME  AND job.JOB_GROUP = tri.JOB_GROUP ");
		nativeSql.append("LEFT JOIN qrtz_cron_triggers AS cron ON cron.TRIGGER_NAME = tri.TRIGGER_NAME AND cron.TRIGGER_GROUP= tri.JOB_GROUP ");
		nativeSql.append("WHERE tri.TRIGGER_TYPE = 'CRON'");
		Object[] params = new  Object[]{};
		if(!StringUtils.isEmpty(quartz.getJobName())){//加入JobName搜索其他条件自行实现
			nativeSql.append(" AND job.JOB_NAME = ?");
			params = new Object[]{quartz.getJobName()};
		}
        List<QuartzEntity> list = dynamicQuery.nativeQueryListModel(QuartzEntity.class, nativeSql.toString(), params);
        for (QuartzEntity quartzEntity : list) {
            JobKey key = new JobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(key);
            quartzEntity.setJobMethodName(jobDetail.getJobDataMap().getString("jobMethodName"));
        }
        return list;
	}

	@Override
	public Long listQuartzEntity(QuartzEntity quartz) {
		StringBuffer nativeSql = new StringBuffer();
		nativeSql.append("SELECT COUNT(*)");
		nativeSql.append("FROM qrtz_job_details AS job LEFT JOIN qrtz_triggers AS tri ON job.JOB_NAME = tri.JOB_NAME ");
		nativeSql.append("LEFT JOIN qrtz_cron_triggers AS cron ON cron.TRIGGER_NAME = tri.TRIGGER_NAME ");
		nativeSql.append("WHERE tri.TRIGGER_TYPE = 'CRON'");
		return dynamicQuery.nativeQueryCount(nativeSql.toString(), new Object[]{});
	}
}
