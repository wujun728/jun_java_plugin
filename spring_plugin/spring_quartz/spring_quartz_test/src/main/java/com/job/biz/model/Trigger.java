package com.job.biz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Trigger implements Serializable {

    private static final long serialVersionUID = -508677676980043982L;

    private String triggerName;// Trigger 名称
    private String cronExpression;// cron表达式

    private String triggerGroup;// Trigger 组名
    private Date startTime;// 开始时间
    private Date endTime;// 结束时间
    private Integer repeatCount;// 执行次数
    private Long repeatInterval;// 执行间隔
    private String status;// Trigger状态

    private Integer triggerType;// 新增Trigger类型：1=Trigger表达式模式，2=执行频率模式，3=指定时间执行模式

    private String selType;// 时间类型：second=秒，minute=分钟
    private Integer intervalTime;// 执行间隔时间

    private static final Map<String, String> statusName = new HashMap<String, String>();
    static {
        statusName.put("ACQUIRED", "运行中");
        statusName.put("PAUSED", "暂停中");
        statusName.put("WAITING", "等待中");
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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
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

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public String getSelType() {
        return selType;
    }

    public void setSelType(String selType) {
        this.selType = selType;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

}
