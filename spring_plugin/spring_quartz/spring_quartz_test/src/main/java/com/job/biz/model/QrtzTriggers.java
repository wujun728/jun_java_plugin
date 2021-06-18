package com.job.biz.model;

import java.util.Calendar;
import java.util.Date;

import com.job.common.constants.Constants;

public class QrtzTriggers {

    private String schedName;
    private String triggerName;
    private String triggerGroup;
    private String jobName;
    private String jobGroup;
    private String description;
    private Long nextFireTime;
    private Long prevFireTime;
    private Integer priority;
    private String triggerState;
    private String triggerType;
    private Long startTime;
    private Long endTime;
    private String calendarName;
    private Integer misfireInstr;
    private String jobData;

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getNextFireTime() {
        if (nextFireTime != null && nextFireTime != 0) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(nextFireTime);
            return c.getTime();
        }
        return null;
    }

    public void setNextFireTime(Long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public Date getPrevFireTime() {
        if (prevFireTime != null && prevFireTime != 0) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(prevFireTime);
            return c.getTime();
        }
        return null;
    }

    public void setPrevFireTime(Long prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public String getState() {
        return Constants.status.get(triggerState);
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public Date getStartTime() {
        if (startTime != null && startTime != 0) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(startTime);
            return c.getTime();
        }
        return null;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        if (endTime != null && endTime != 0) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(endTime);
            return c.getTime();
        }
        return null;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public Integer getMisfireInstr() {
        return misfireInstr;
    }

    public void setMisfireInstr(Integer misfireInstr) {
        this.misfireInstr = misfireInstr;
    }

    public String getJobData() {
        return jobData;
    }

    public void setJobData(String jobData) {
        this.jobData = jobData;
    }

}
