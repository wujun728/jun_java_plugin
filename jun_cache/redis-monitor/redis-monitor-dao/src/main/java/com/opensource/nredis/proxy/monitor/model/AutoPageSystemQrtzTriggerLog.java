package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
import java.util.Date;
/**
* model AutoPageSystemQrtzTriggerLog
*
* @author liubing
* @date 2016/12/30 08:41
* @version v1.0.0
*/
public class AutoPageSystemQrtzTriggerLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8833774998711504554L;
	/**
	 * 主键
	 */
    private Integer id;
	/**
	 * 任务组
	 */
    private Integer jobGroup;
    
    private String jobGroupName;
	/**
	 * 任务名
	 */
    private String jobName;
	/**
	 * cron表达式
	 */
    private String jobCron;
	/**
	 * 任务描述
	 */
    private String jobDesc;
	/**
	 * 执行方式
	 */
    private Integer jobClass;
    
    private String jobClassName;
	/**
	 * 任务数据
	 */
    private String jobData;
	/**
	 * 调度时间
	 */
    private Date triggerTime;
    private String triggerTimeString;
	/**
	 * 调度结果
	 */
    private String triggerStatus;
	/**
	 * 调度日志
	 */
    private String triggerMsg;
	/**
	 * 执行时间
	 */
    private Date handleTime;
    
    private String handleTimeString;

	/**
	 * 执行状态
	 */
    private String handleStatus;
	/**
	 * 执行日志
	 */
    private String handleMsg;
 

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setJobGroup(Integer jobGroup){
        this.jobGroup = jobGroup;
    }
    public Integer getJobGroup(){
        return this.jobGroup;
    }

    public void setJobName(String jobName){
        this.jobName = jobName;
    }
    public String getJobName(){
        return this.jobName;
    }

    public void setJobCron(String jobCron){
        this.jobCron = jobCron;
    }
    public String getJobCron(){
        return this.jobCron;
    }

    public void setJobDesc(String jobDesc){
        this.jobDesc = jobDesc;
    }
    public String getJobDesc(){
        return this.jobDesc;
    }

    public void setJobClass(Integer jobClass){
        this.jobClass = jobClass;
    }
    public Integer getJobClass(){
        return this.jobClass;
    }

    public void setJobData(String jobData){
        this.jobData = jobData;
    }
    public String getJobData(){
        return this.jobData;
    }

    public void setTriggerTime(Date triggerTime){
        this.triggerTime = triggerTime;
    }
    public Date getTriggerTime(){
        return this.triggerTime;
    }

    public void setTriggerStatus(String triggerStatus){
        this.triggerStatus = triggerStatus;
    }
    public String getTriggerStatus(){
        return this.triggerStatus;
    }

    public void setTriggerMsg(String triggerMsg){
        this.triggerMsg = triggerMsg;
    }
    public String getTriggerMsg(){
        return this.triggerMsg;
    }

    public void setHandleTime(Date handleTime){
        this.handleTime = handleTime;
    }
    public Date getHandleTime(){
        return this.handleTime;
    }

    public void setHandleStatus(String handleStatus){
        this.handleStatus = handleStatus;
    }
    public String getHandleStatus(){
        return this.handleStatus;
    }

    public void setHandleMsg(String handleMsg){
        this.handleMsg = handleMsg;
    }
    public String getHandleMsg(){
        return this.handleMsg;
    }
	/**
	 * @return the jobGroupName
	 */
	public String getJobGroupName() {
		return jobGroupName;
	}
	/**
	 * @param jobGroupName the jobGroupName to set
	 */
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	/**
	 * @return the triggerTimeString
	 */
	public String getTriggerTimeString() {
		return triggerTimeString;
	}
	/**
	 * @param triggerTimeString the triggerTimeString to set
	 */
	public void setTriggerTimeString(String triggerTimeString) {
		this.triggerTimeString = triggerTimeString;
	}
	/**
	 * @return the handleTimeString
	 */
	public String getHandleTimeString() {
		return handleTimeString;
	}
	/**
	 * @param handleTimeString the handleTimeString to set
	 */
	public void setHandleTimeString(String handleTimeString) {
		this.handleTimeString = handleTimeString;
	}
	/**
	 * @return the jobClassName
	 */
	public String getJobClassName() {
		return jobClassName;
	}
	/**
	 * @param jobClassName the jobClassName to set
	 */
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
    
    
}