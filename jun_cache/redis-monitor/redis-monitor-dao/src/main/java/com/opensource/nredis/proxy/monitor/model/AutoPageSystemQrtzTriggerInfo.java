package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
import java.util.Date;
/**
* model AutoPageSystemQrtzTriggerInfo
*
* @author liubing
* @date 2016/12/30 08:41
* @version v1.0.0
*/
public class AutoPageSystemQrtzTriggerInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6804733628662460098L;
	/**
	 * 主键
	 */
    private Integer id;
	/**
	 * 任务组
	 */
    private Integer jobGroup;
    
    /**
     * 任务组名称
     */
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

	/**
	 * 创建时间
	 */
    private Date addTime;
    
    /**
     * 
     */
    private String addTimeString;
	/**
	 * 修改时间
	 */
    private Date updateTime;
	/**
	 * 作者
	 */
    private String author;
	/**
	 * 报警邮件
	 */
    private String alarmEmail;
	/**
	 * 报警阀值
	 */
    private Integer alarmThreshold;
    
    private String jobClassName;
    
    private String jobStatus;	// 任务状态 【base on quartz】
    
    
    private String createUserName;//创建人
    
    
    private String className;//类名
    


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

    public void setAddTime(Date addTime){
        this.addTime = addTime;
    }
    public Date getAddTime(){
        return this.addTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
    public Date getUpdateTime(){
        return this.updateTime;
    }

    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }

    public void setAlarmEmail(String alarmEmail){
        this.alarmEmail = alarmEmail;
    }
    public String getAlarmEmail(){
        return this.alarmEmail;
    }

    public void setAlarmThreshold(Integer alarmThreshold){
        this.alarmThreshold = alarmThreshold;
    }
    public Integer getAlarmThreshold(){
        return this.alarmThreshold;
    }
	/**
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}
	/**
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
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
	 * @return the addTimeString
	 */
	public String getAddTimeString() {
		return addTimeString;
	}
	/**
	 * @param addTimeString the addTimeString to set
	 */
	public void setAddTimeString(String addTimeString) {
		this.addTimeString = addTimeString;
	}
	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}	
    
}