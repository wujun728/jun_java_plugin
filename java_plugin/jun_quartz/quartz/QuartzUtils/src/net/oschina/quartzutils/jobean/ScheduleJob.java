package net.oschina.quartzutils.jobean;

import java.util.Date;
/**
 * 
 * @author zdc
 *
 */
public class ScheduleJob {
	public static final String STATUS_RUNNING = "1";// 运行状态
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	public static final String ERROE_STOP = "1";
	public static final String ERROR_NO_STOP = "0";
	private String errorStatus;

	public String getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}

	/**
	 * 不触发立即执行 等待下次Cron触发频率到达时刻开始按照Cron频率依次执行 就是什么也不做
	 */
	public static final String MISFIRE_STATUS_DO_NOTHING = "0";
	/**
	 * 以错过的第一个频率时间立刻开始执行 重做错过的所有频率周期后 当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
	 */
	public static final String MISFIRE_STATUS_IGNORE_MISFIRES = "1";
	/**
	 * 以当前时间为触发频率立刻触发一次执行 然后按照Cron频率依次执行
	 */
	public static final String MISFIRE_STATUS_FIREANDPROCEED = "2";
	private String misfireStatus;

	public String getMisfireStatus() {
		return misfireStatus;
	}

	public void setMisfireStatus(String misfireStatus) {
		this.misfireStatus = misfireStatus;
	}

	private Long jobId;

	private Date createTime;
	private Date startTime;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private Date endTime;

	private Date updateTime;
	/**
	 * 任务名称
	 */
	private String jobName;
	/**
	 * 任务分组
	 */
	private String jobGroup;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public String getSpringId() {
		return springId;
	}

	public void setSpringId(String springId) {
		this.springId = springId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * 任务状态 是否启动任务
	 */
	private String jobStatus;
	/**
	 * cron表达式
	 */
	private String cronExpression;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 任务执行时调用哪个类的方法 包名+类名
	 */
	private String beanClass;
	/**
	 * 任务是否有状态
	 */
	private String isConcurrent;
	/**
	 * spring bean
	 */
	private String springId;
	/**
	 * 任务调用的方法名
	 */
	private String methodName;

}