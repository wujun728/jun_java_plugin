package org.ssm.dufy.entity;

import java.util.Date;

import org.quartz.JobDataMap;

public class JobEntity {

    private int jobId;

    private String jobType;

    private String jobGroup;

    private String jobName;

    private String triggerName;

    private String triggerGroupName;

    private String cronExpr;

    private Date previousFireTime;

    private Date nextFireTime;

    private String jobStatus;

    private long runTimes;

    private long duration;

    private Date startTime;

    private Date endTime;

    private String jobMemo;

    private String jobClass;

    private String jobMethod;

    private String jobObject;

    private int count;

    private JobDataMap jobDataMap;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public long getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(long runTimes) {
        this.runTimes = runTimes;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

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

    public String getJobMemo() {
        return jobMemo;
    }

    public void setJobMemo(String jobMemo) {
        this.jobMemo = jobMemo;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public void setJobMethod(String jobMethod) {
        this.jobMethod = jobMethod;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getJobObject() {
        return jobObject;
    }

    public void setJobObject(String jobObject) {
        this.jobObject = jobObject;
    }

    public JobDataMap getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

}
